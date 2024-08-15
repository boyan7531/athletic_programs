package bg.softuni.athletic_program_side_project.controller;

import bg.softuni.athletic_program_side_project.entity.Run;
import bg.softuni.athletic_program_side_project.service.RunService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/runs")
public class RunController {

    private final RunService runService;

    public RunController(RunService runService) {
        this.runService = runService;
    }

    @GetMapping
    public List<Run> getAllRuns() {
        return runService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Run> getRunById(@PathVariable Long id) {
        return runService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Run createRun(@RequestBody Run run) {
        return runService.save(run);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Run> updateRun(@PathVariable Long id, @RequestBody Run runDetails) {
        return runService.findById(id)
                .map(run -> {
                    run.setTitle(runDetails.getTitle());
                    run.setHours(runDetails.getHours());
                    run.setMinutes(runDetails.getMinutes());
                    run.setSeconds(runDetails.getSeconds());
                    run.setKilometers(runDetails.getKilometers());
                    run.setMeters(runDetails.getMeters());
                    return ResponseEntity.ok(runService.save(run));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRun(@PathVariable Long id) {
        return runService.findById(id)
                .map(run -> {
                    runService.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}