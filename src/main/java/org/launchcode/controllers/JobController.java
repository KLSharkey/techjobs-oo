package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.data.JobFieldData;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.ArrayList;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();//all available jobs/employers/etc.

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {
        ArrayList<Job> listJobs = jobData.findAll(); //get all jobs
        for (Job job : listJobs) {
            int uniqueID = job.getId();
            if (uniqueID == id) {
                model.addAttribute("job", job);
            }
        }

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) { //jobForm has JobField ids and values
//add values to job

        if (errors.hasErrors()) {
            model.addAttribute("name", "Add Name");
            return "new-job";
        }
            Job newJob = new Job(jobForm.getName(),
                    jobData.getEmployers().findById(jobForm.getEmployerId()),
                    jobData.getLocations().findById(jobForm.getLocationId()),
                    jobData.getPositionTypes().findById(jobForm.getPositionTypeId()),
                    jobData.getCoreCompetencies().findById(jobForm.getCoreCompentencyId()));

            jobData.add(newJob);
            int id = newJob.getId();

            return "redirect:?id=" + id;

        }
    }

