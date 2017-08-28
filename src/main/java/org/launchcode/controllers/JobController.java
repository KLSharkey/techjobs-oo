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
        int locationID = 0;
        int coreCompID = 0;
        int positionID = 0;
        if (errors.hasErrors()) {
            model.addAttribute("name", "Add Name");
            return "new-job";
        } else {
            //Location locationID = new Location();
            //CoreCompetency coreCompID = new CoreCompetency();

            for (Location item : jobForm.getLocations()) {
                if (item.getValue().equals(jobForm.getLocation())) {
                    locationID = item.getId();
                }
            }
            for (CoreCompetency item : jobForm.getCoreCompetencies()) {
                if (item.getValue().equals(jobForm.getCoreCompentency())) {
                   coreCompID = item.getId();
                }
            }
            for (PositionType item : jobForm.getPositionTypes()) {
                if (item.getValue().equals(jobForm.getPositionType())) {
                    positionID = item.getId();
                }
            }
            Job newJob = new Job(jobForm.getName(), jobData.getEmployers().findById(jobForm.getEmployerId()), jobData.getLocations().findById(locationID),
                    jobData.getPositionTypes().findById(positionID), jobData.getCoreCompetencies().findById(coreCompID));

            jobData.add(newJob);
            int id = newJob.getId();
            return "redirect:?id=" + id;
            /*int id = 0;
            int employerID = jobForm.getEmployerId();
            String name = jobForm.getName();
            String location = jobForm.getLocation();
            Location location1 = new Location(location);
            String coreCompentency = jobForm.getCoreCompentency();
            CoreCompetency coreCompetency1 = new CoreCompetency(coreCompentency);
            String positionType = jobForm.getPositionType();
            PositionType positionType1 = new PositionType(positionType);
            ArrayList<Employer> employers = jobForm.getEmployers();
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
                            id = newJob.getId();
                            model.addAttribute("job", newJob);
                            //model.addAttribute("id", newJob.getId());
                        }

                    }
                }
            }*/

            //return "redirect:?id=" + id;

        }
    }
}
