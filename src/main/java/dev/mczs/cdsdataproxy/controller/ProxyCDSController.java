package dev.mczs.cdsdataproxy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static dev.mczs.cdsdataproxy.CdsDataProxyApplication.CONFIG;
import static dev.mczs.cdsdataproxy.CdsDataProxyApplication.REST_TEMPLATE;

@RestController
@RequestMapping("/api/v4")
public class ProxyCDSController {
    private final Long contestId = CONFIG.getContestId();

    // ------------------------ 1. 透传：/api/v4/contests -----------------------
    @GetMapping("/contests")
    public String contests() {
        String cdsUrl = CONFIG.getCdsApiUrl() + ":" + CONFIG.getServerPort() + "/api/contests/" + contestId;
        ResponseEntity<String> response = REST_TEMPLATE.getForEntity(cdsUrl, String.class);
        // 原封不动返回，Python 中返回数组格式，这里保持一致（包装成数组）
        return "[" + response.getBody() + "]";
    }

    // ------------------------ 2. 透传：/api/v4/contests/{cid}（竞赛详情）------------------------
    @GetMapping("/contests/{cid}")
    public String contestInfo(@PathVariable String cid) {
        String cdsUrl = CONFIG.getCdsApiUrl() + ":" + CONFIG.getServerPort() + "/api/contests/" + cid;
        ResponseEntity<String> response = REST_TEMPLATE.getForEntity(cdsUrl, String.class);
        return response.getBody(); // 纯透传，不修改任何字段
    }

    // ------------------------ 3. 透传：/api/v4/contests/{cid}/teams（队伍列表）------------------------
    @GetMapping("/contests/{cid}/teams")
    public String teams(@PathVariable String cid) {
        String cdsUrl = CONFIG.getCdsApiUrl() + ":" + CONFIG.getServerPort() + "/api/contests/" + cid + "/teams";
        ResponseEntity<String> response = REST_TEMPLATE.getForEntity(cdsUrl, String.class);
        return response.getBody();
    }

    // ------------------------ 4. 透传：/api/v4/contests/{cid}/PROBLEMS（题目列表）------------------------
    @GetMapping("/contests/{cid}/problems")
    public String problems(@PathVariable String cid) {
        String cdsUrl = CONFIG.getCdsApiUrl() + ":" + CONFIG.getServerPort() + "/api/contests/" + cid + "/PROBLEMS";
        ResponseEntity<String> response = REST_TEMPLATE.getForEntity(cdsUrl, String.class);
        return response.getBody();
    }

    // ------------------------ 5. 透传：/api/v4/contests/{cid}/submissions（提交列表）------------------------
    @GetMapping("/contests/{cid}/submissions")
    public String submissions(@PathVariable String cid) {
        String cdsUrl = CONFIG.getCdsApiUrl() + ":" + CONFIG.getServerPort() + "/api/contests/" + cid + "/submissions";
        ResponseEntity<String> response = REST_TEMPLATE.getForEntity(cdsUrl, String.class);
        return response.getBody();
    }

    // ------------------------ 6. 透传：/api/v4/contests/{cid}/runs ------------------------
    @GetMapping("/contests/{cid}/runs")
    public String runs(@PathVariable String cid) {
        String cdsUrl = CONFIG.getCdsApiUrl() + ":" + CONFIG.getServerPort() + "/api/contests/" + cid + "/submissions";
        ResponseEntity<String> response = REST_TEMPLATE.getForEntity(cdsUrl, String.class);
        return response.getBody();
    }

    // ------------------------ 7. 透传：/api/v4/contests/{cid}/judgements ------------------------
    @GetMapping("/contests/{cid}/judgements/{judgeId}")
    public String judgements(@PathVariable("cid") String cid, @PathVariable("judgeId")String judgeId) {
        String cdsUrl = CONFIG.getCdsApiUrl() + ":" + CONFIG.getServerPort() + "/api/contests/" + cid + "/judgements/" + judgeId;
        ResponseEntity<String> response = REST_TEMPLATE.getForEntity(cdsUrl, String.class);
        return response.getBody();
    }
}
