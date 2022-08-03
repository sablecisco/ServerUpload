package Focus_Zandi.version1.web.controller;

import Focus_Zandi.version1.domain.Records;
import Focus_Zandi.version1.domain.dto.*;
import Focus_Zandi.version1.web.service.RecordService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    // 오늘 공부기록 저장
    @PostMapping("/saveRecords/today")
    public int receiveRecord (@RequestBody RecordsDto recordsDto, Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        String username = getUsername(authentication);
        recordService.save(username, recordsDto);
        return response.getStatus();
    }

    // api시트에서 못 찾음
    @GetMapping("/showRecords")
    public void showTodayRecord(Authentication authentication, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = getUsername(authentication);
        String timeStamp = LocalDate.now().toString();
        findRecord(username, timeStamp, response);
    }

    // 지정 날짜로 검색
    // 항상 ?date=YYYY-MM-DD 형식을 지킬것
    // 특정 날짜의 데이터 조회
    @GetMapping("/records")
    public void showRecordByDate(@RequestParam("date") String date, Authentication authentication, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = getUsername(authentication);
        findRecord(username, date, response);
    }

    // 월별 데이터 반환 (날짜와 총 집중시간)
    // 이번달 공부내역 조회
    @GetMapping("/records/monthly")
    public List<MonthlyRecordsDto> monthlyRecordsV2(@RequestParam String month, Authentication authentication, HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<MonthlyRecordsDto> monthly = recordService.findMonthlyV2(month, getUsername(authentication));
        if (monthly.isEmpty()) {
            response.sendError(400);
        }
        return monthly;
    }

    // 친구들 랭킹 -일-
    //정렬로 보내는건 미구현
    @GetMapping("/friendRanking")
    public List<MyFollowersDto> dailyRanks(Authentication authentication, HttpServletRequest request) {
        return recordService.dailyRanks(getUsername(authentication));
    }

    private void findRecord(String username, String timeStamp, HttpServletResponse response) throws IOException {
        Records record = recordService.findRecordByTimeStamp(username, timeStamp);
        if (record == null) {
            response.setStatus(204);
            response.sendError(400, "No data");
            return;
        }
        RecordReturnerDto recordReturnerDto = new RecordReturnerDto(record);
        String json = new Gson().toJson(recordReturnerDto);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

//    private String getUsername (HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//
//        SecurityContextImpl context = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
//        return context.getAuthentication().getName();
//    }

    private String getUsername(Authentication authentication) {
        String name = authentication.getName();
        return name;
    }
}

