package br.com.coffeeandit.app.business;

import br.com.coffeeandit.app.entity.Travel;
import br.com.coffeeandit.app.transport.TravelDTO;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Service
@Transactional
@Log
public class TravelBusiness {

    @PersistenceContext
    private EntityManager entityManager;

    public static final String ID = "id";

    public Travel create(final TravelDTO travelDTO)  {
        Travel entity = transform.apply(travelDTO);
        if (exists(entity)) {
            throw new NonUniqueResultException("Travel already exists");
        }
        getEntityManager().persist(entity);
        return entity;
    }

    private boolean exists(final Travel entity) {
        if (Objects.nonNull(entity.getId())) {
            if (countById(entity.getId()).compareTo(Long.valueOf("0")) > 0) {
                return true;
            }
        }
        return false;
    }

    public List<Travel> findAll() {
        final CriteriaQuery<Travel> cq = getEntityManager().getCriteriaBuilder()
                .createQuery(Travel.class);
        cq.select(cq.from(Travel.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public Travel edit(final TravelDTO travelDTO) {
        travelDTO.printTravelInformation();

        Travel entity = transform.apply(travelDTO);
        return getEntityManager().merge(entity);
    }

    public void remove(final Travel entity) {
        if (exists(entity)) {
            getEntityManager().remove(getEntityManager().merge(entity));
        }
    }

    public Long countById(final Long id) {
        final CriteriaBuilder qb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        cq.select(qb.count(cq.from(Travel.class)));
        final Root<Travel> idEntityRoot = cq.from(Travel.class);
        cq.where(qb.equal(idEntityRoot.get(ID), id));
        return entityManager.createQuery(cq).getSingleResult();
    }

    public Optional<Travel> findById(final Long id) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Travel> criteriaQuery = criteriaBuilder.createQuery(Travel.class);
        final Root<Travel> rootEntity = criteriaQuery.from(Travel.class);
        final Predicate predicateKeyValue = criteriaBuilder.equal(rootEntity.get(ID), id);
        criteriaQuery.where(predicateKeyValue);
        final TypedQuery<Travel> q = entityManager.createQuery(criteriaQuery);
        try {
            return Optional.ofNullable(q.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public Function<TravelDTO, Travel> transform = dto -> {

        checkTravelDate(dto);
        var travel = new Travel();
        travel.setId(dto.getId());
        travel.setIdCar(dto.getIdCar());
        travel.setIdFlight(dto.getIdFlight());
        //*** RECORD
       // travel.setHotelName(dto.getHotelRecord().name());
        //***
        travel.setTravelDateTime(dto.getTravelDateTime());
        return travel;
    };

    private void checkTravelDate(TravelDTO dto) {
        if (Objects.nonNull(dto.getTravelDateTime())) {
            LocalDateTime todayTime = LocalDateTime.now();
            if (todayTime.isAfter(dto.getTravelDateTime())) {
                throw new RuntimeException("TravelDate cannot be before today");
            }

//            DayOfWeek dayOfWeek = todayTime.getDayOfWeek();
//            switch (dayOfWeek) {
//                case MONDAY, WEDNESDAY, FRIDAY:
//                    log.info("It's cheaper to schedule your travel at this day of week");
//                    break;
//                case TUESDAY, THURSDAY, SATURDAY:
//                    break;
//                case SUNDAY:
//                    log.info("Travel at sunday?");
//                    break;
//            }
        }
    }

    private void printTravelDateOperations(LocalDateTime travelDateTime) {
        LocalDate today = travelDateTime.toLocalDate();

        //Current Date
        log.info("Current Date: " + today);

        //Current Date using LocalDate and LocalTime
        LocalDateTime todayTime = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        log.info("Current DateTime: " + todayTime);

        //Get the Year, check if it's leap year
        log.info("Year "+ today.getYear()+" is Leap Year? "+ today.isLeapYear());

        //Compare two LocalDate for before and after
        log.info("Today is before 01/01/2020? "+ today.isBefore(LocalDate.of(2020,1,1)));

        //Create LocalDateTime from LocalDate
        log.info("Current Time: "+ today.atTime(LocalTime.now()));

        //plus and minus operations
        log.info("10 days after today will be "+ today.plusDays(10));
        log.info("3 weeks after today will be "+ today.plusWeeks(3));
        log.info("20 months after today will be "+ today.plusMonths(20));

        log.info("10 days before today will be "+ today.minusDays(10));
        log.info("3 weeks before today will be "+ today.minusWeeks(3));
        log.info("20 months before today will be "+ today.minusMonths(20));

        //Temporal adjusters for adjusting the dates
        log.info("First date of this travel month: "+ today.with(TemporalAdjusters.firstDayOfMonth()));

        LocalDate lastDayOfYear = today.with(TemporalAdjusters.lastDayOfYear());
        log.info("Last date of this travel year:  " +lastDayOfYear);

        Period period = today.until(lastDayOfYear);
        log.info("Period Format: "+period);
        log.info("Months remaining in the year: "+period.getMonths());

        log.info("Default format of LocalDate:  "+travelDateTime);

        //specific format
        log.info(travelDateTime.format(DateTimeFormatter.ofPattern("d::MMM::uuuu")));
        log.info(travelDateTime.format(DateTimeFormatter.BASIC_ISO_DATE));

        LocalDateTime dateTime = LocalDateTime.now();
        //default format
        log.info("Default format of LocalDateTime: "+dateTime);

        //specific format
        log.info(dateTime.format(DateTimeFormatter.ofPattern("d::MMM::uuuu HH::mm::ss")));
        log.info(dateTime.format(DateTimeFormatter.BASIC_ISO_DATE));

        Instant timestamp = Instant.now();
        //default format
        log.info("Default format of Instant: "+timestamp);

        //Parse examples
        String str = "2020-04-08 12:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        dateTime = LocalDateTime.parse(str, formatter);
        log.info("Default format after parsing: "+ dateTime);

        //Date to Instant
        Instant timestampLegacy = new Date().toInstant();

        //Now we can convert Instant to LocalDateTime or other similar classes
        LocalDateTime date = LocalDateTime.ofInstant(timestampLegacy, ZoneId.of(ZoneId.SHORT_IDS.get("BET")));
        log.info("Date: "+date);

        //Calendar to Instant
        Instant time = Calendar.getInstance().toInstant();
        log.info(time.toString());

        //TimeZone to ZoneId
        ZoneId defaultZone = TimeZone.getDefault().toZoneId();
        log.info(defaultZone.toString());

        //ZonedDateTime from specific Calendar
        ZonedDateTime gregorianCalendarDateTime = new GregorianCalendar().toZonedDateTime();
        log.info(gregorianCalendarDateTime.toString());

        //Date API to Legacy classes
        Date dateLegacy = Date.from(Instant.now());
        log.info(dateLegacy.toString());

        TimeZone tz = TimeZone.getTimeZone(defaultZone);
        log.info(tz.toString());

        GregorianCalendar gc = GregorianCalendar.from(gregorianCalendarDateTime);
        log.info(gc.toString());
    }

    public void processTravelNotifications(Long id) {
        CompletableFuture.runAsync(() -> initNotifications(id));
    }

    private void initNotifications(Long id) {
        CompletableFuture.supplyAsync(() -> findById(id))
                .thenApplyAsync(this::sendMsg)
                .thenAcceptAsync(this::notify);
    }

    private Optional<Travel> sendMsg(Optional<Travel> travel) {
        waitASecond();
        log.info("Hi! Your travel reservation its complete! Congratulations");
        return travel;
    }

    private void notify(Optional<Travel> travel) {
        waitASecond();
        log.info("Notify interested objects in travel");
    }

    private void waitASecond() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
