package net.travisford.courseomatic.web;

import net.travisford.courseomatic.Course;
import net.travisford.courseomatic.CourseTile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class TileDemoController {

    @GetMapping("/tiledemo")
    public String showTileDemo(){
        return "tiledemo";
    }

    @ModelAttribute
    public void addAttributes(Model model)
    {
        List<CourseTile> tiles = new ArrayList<>();
        tiles.add(new CourseTile("CS", "200", "Programming 1"               , new ArrayList<String>(Arrays.asList("MATH-173"))));
        tiles.add(new CourseTile("CS", "201", "Discrete Structures"         , new ArrayList<String>(Arrays.asList("MATH-173"))));
        tiles.add(new CourseTile("CS", "207", "Programming 2"               , new ArrayList<String>(Arrays.asList("CS-200"))));
        tiles.add(new CourseTile("CS", "300", "Client Side Web Development" , new ArrayList<String>(Arrays.asList("CS-200"))));
        tiles.add(new CourseTile("CS", "300", "Client Side Web Development" , new ArrayList<String>(Arrays.asList("CS-200"))));
        tiles.add(new CourseTile("CS", "301", "Computer Organization"       , new ArrayList<String>(Arrays.asList("CS-200", "CS-201"))));
        tiles.add(new CourseTile("CS", "304", "Data Structures"             , new ArrayList<String>(Arrays.asList("CS-201", "CS-207"))));
        tiles.add(new CourseTile("CS", "308", "Operating Systems"           , new ArrayList<String>(Arrays.asList("CS-207", "CS-301"))));
        tiles.add(new CourseTile("CS", "315", "Modern Database Management"  , new ArrayList<String>(Arrays.asList("CS-207"))));
        tiles.add(new CourseTile("CS", "319", "Fundamentals Of Software Eng", new ArrayList<String>(Arrays.asList("CS-304"))));

        model.addAttribute("tiles", tiles);
    }

}