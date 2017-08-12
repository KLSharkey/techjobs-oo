package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.ArrayList;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {
        ArrayList<Job> listJobs = jobData.findAll();
        for (Job job : listJobs) {
            int uniqueID = job.getId();
            if (uniqueID == id) {
                model.addAttribute("job", job);
            }
        }

        // TODO #1 - get the Job with the given ID and pass it into the view
        //Job.getID()

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {
        int employerID = jobForm.getEmployerId();
        String name = jobForm.getName();
        String location = jobForm.getLocation();
        Location location1 = new Location(location);
        String coreCompentency = jobForm.getCoreCompentency();
        CoreCompetency coreCompetency1 = new CoreCompetency(coreCompentency);
        String positionType = jobForm.getPositionType();
        PositionType positionType1 = new PositionType(positionType);
        ArrayList<Employer> employers = jobForm.getEmployers();
        //Employer employer = null;
        if (errors.hasErrors()) {
            model.addAttribute("name", "Add Name");
            return "new-job";
        } else {
            for (Employer theEmployer : employers) {
                int employersID = theEmployer.getId();
                if (employersID == employerID) {
                    String employerValue = theEmployer.getValue();
                    if (employerValue != null && name != null) {
                        Employer employer = new Employer(employerValue);
                        Job newJob = new Job(name, employer, location1, positionType1, coreCompetency1);
                        jobData.add(newJob);
                        model.addAttribute("job", newJob);
                    }

                    /*String Error = "Must include a Job Name.";
                    model.addAttribute("Error", Error);
                    return "new-job";*/
                }
            }
        }
        //Job newJob = new Job(name, employer, location1, positionType1, coreCompetency1);
        //jobData.add(newJob);
        //String employer = jobForm.gete

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        //return "job-detail";


        return "job-detail";
    }
}
