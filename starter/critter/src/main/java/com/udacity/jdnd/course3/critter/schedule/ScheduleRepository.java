package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByEmployees_Id(long employeeId);

    List<Schedule> findByPets_Id(long petId);

    List<Schedule> findByPets_OwnerId(long customerId);

//    List<Schedule> findByEmployees_DaysAvailableInAndEmployees_Allocated(Set<DayOfWeek> daysAvailable , Boolean allocated);

//    List<Schedule> findSchedulesByWorkDay(DayOfWeek workDay);

    List<Schedule> findSchedulesByEmployeesDaysAvailableIn(Set<DayOfWeek> daysAvailable);
}
