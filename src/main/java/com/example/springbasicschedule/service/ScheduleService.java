package com.example.springbasicschedule.service;

import com.example.springbasicschedule.dto.*;
import com.example.springbasicschedule.entity.Schedule;
import com.example.springbasicschedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public CreateScheduleResponse save(CreateScheduleRequest request) {
        Schedule schedule = new Schedule(request.getTitle(), request.getContent(), request.getName(), request.getPassword());
        Schedule saved = scheduleRepository.save(schedule);
        return new CreateScheduleResponse(saved.getScheduleId(), saved.getTitle(), saved.getContent(), saved.getName(), saved.getCreatedAt(), saved.getModifiedAt());
    }

    @Transactional(readOnly = true)
    public List<GetScheduleResponse> findAll() {
        List<Schedule> schedules = scheduleRepository.findAll();
        List<GetScheduleResponse> dtos = new ArrayList<>();
        for (Schedule schedule : schedules) {
            GetScheduleResponse dto = new GetScheduleResponse(schedule.getScheduleId(), schedule.getTitle(), schedule.getContent(), schedule.getName(), schedule.getCreatedAt(), schedule.getModifiedAt());
            dtos.add(dto);
        }
        return dtos;
    }

    @Transactional(readOnly = true)
    public @Nullable GetScheduleResponse findOne(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalArgumentException("Schedule id " + scheduleId + " not found")
        );
        return new GetScheduleResponse(schedule.getScheduleId(), schedule.getTitle(), schedule.getContent(), schedule.getName(), schedule.getCreatedAt(), schedule.getModifiedAt());
    }

    public UpdateScheduleResponse update(Long scheduleId, UpdateScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalArgumentException("Schedule id " + scheduleId + " not found")
        );
        if(!schedule.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        schedule.update(request.getTitle(), request.getName());
        return new UpdateScheduleResponse(schedule.getScheduleId(), schedule.getTitle(), schedule.getContent(), schedule.getName(), schedule.getCreatedAt(), schedule.getModifiedAt());
    }

    public void delete(Long scheduleId) {
        boolean existence = scheduleRepository.existsById(scheduleId);
        if(!existence) {
            throw new IllegalArgumentException("Schedule id " + scheduleId + " not found");
        }
        scheduleRepository.deleteById(scheduleId);
    }
}
