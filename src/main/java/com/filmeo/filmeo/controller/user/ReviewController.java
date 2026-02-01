package com.filmeo.filmeo.controller.user;

import com.filmeo.filmeo.model.entity.Artist;
import com.filmeo.filmeo.model.entity.ArtistReview;
import com.filmeo.filmeo.model.entity.Production;
import com.filmeo.filmeo.model.entity.ProductionReview;
import com.filmeo.filmeo.model.entity.User;
import com.filmeo.filmeo.model.service.ArtistReviewService;
import com.filmeo.filmeo.model.service.ArtistService;
import com.filmeo.filmeo.model.service.ProductionReviewService;
import com.filmeo.filmeo.model.service.ProductionService;
import com.filmeo.filmeo.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReviewController {

    @Autowired
    private ProductionReviewService pReviewService;

    @Autowired
    private ArtistReviewService aReviewService;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private ProductionService productionService;

    @Autowired
    private UserService userService;

    private User getCurrentUser() {
        Authentication authentication =
            SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            return userService.getByUsername(username);
        }
        return null;
    }

    /* ==================== ARTIST REVIEWS ==================== */

    @GetMapping("/artists/{id}/review")
    public String showArtistReviewForm(@PathVariable int id, Model model) {
        Artist artist = artistService.getById(id);
        ArtistReview review = new ArtistReview();
        model.addAttribute("artist", artist);
        model.addAttribute("review", review);
        return "user/artist/form";
    }

    @PostMapping("/artists/{id}/review")
    public String submitArtistReview(
        @PathVariable int id,
        @ModelAttribute ArtistReview review,
        BindingResult br,
        Model model
    ) {
        if (br.hasErrors()) {
            Artist artist = artistService.getById(id);
            model.addAttribute("artist", artist);
            model.addAttribute("review", review);
            return "user/artist/form";
        }
        review.setArtist(artistService.getById(id));
        review.setUser(getCurrentUser());
        aReviewService.update(review);
        return "redirect:/artists/" + id;
    }

    @GetMapping("/artists/{id}/review/delete/{reviewId}")
    public String deleteArtistReview(
        @PathVariable int id,
        @PathVariable int reviewId
    ) {
        ArtistReview review = aReviewService.getById(reviewId);
        aReviewService.delete(review);
        return "redirect:/artists/" + id;
    }

    @GetMapping("/artists/{id}/review/edit/{reviewId}")
    public String editArtistReviewForm(
        @PathVariable int id,
        @PathVariable int reviewId,
        Model model
    ) {
        Artist artist = artistService.getById(id);
        ArtistReview review = aReviewService.getById(reviewId);
        model.addAttribute("artist", artist);
        model.addAttribute("review", review);
        return "user/artist/form";
    }

    @PostMapping("/artists/{id}/review/edit/{reviewId}")
    public String updateArtistReview(
        @PathVariable int id,
        @PathVariable int reviewId,
        @ModelAttribute ArtistReview review,
        BindingResult br,
        Model model
    ) {
        if (br.hasErrors()) {
            Artist artist = artistService.getById(id);
            model.addAttribute("artist", artist);
            model.addAttribute("review", review);
            return "user/artist/form";
        }
        review.setId(reviewId);
        review.setArtist(artistService.getById(id));
        review.setUser(getCurrentUser());
        aReviewService.update(review);
        return "redirect:/artists/" + id;
    }

    /* ==================== PRODUCTION REVIEWS ==================== */

    @GetMapping("/productions/{id}/review")
    public String showProductionReviewForm(@PathVariable int id, Model model) {
        Production production = productionService.getById(id);
        ProductionReview review = new ProductionReview();
        model.addAttribute("production", production);
        model.addAttribute("review", review);
        return "user/production/form";
    }

    @PostMapping("/productions/{id}/review")
    public String submitProductionReview(
        @PathVariable int id,
        @ModelAttribute ProductionReview review,
        BindingResult br,
        Model model
    ) {
        if (br.hasErrors()) {
            Production production = productionService.getById(id);
            model.addAttribute("production", production);
            model.addAttribute("review", review);
            return "user/production/form";
        }
        review.setProduction(productionService.getById(id));
        review.setUser(getCurrentUser());
        pReviewService.update(review);
        return "redirect:/productions/" + id;
    }

    @GetMapping("/productions/{id}/review/delete/{reviewId}")
    public String deleteProductionReview(
        @PathVariable int id,
        @PathVariable int reviewId
    ) {
        ProductionReview review = pReviewService.getById(reviewId);
        pReviewService.delete(review);
        return "redirect:/productions/" + id;
    }

    @GetMapping("/productions/{id}/review/edit/{reviewId}")
    public String editProductionReviewForm(
        @PathVariable int id,
        @PathVariable int reviewId,
        Model model
    ) {
        Production production = productionService.getById(id);
        ProductionReview review = pReviewService.getById(reviewId);
        model.addAttribute("production", production);
        model.addAttribute("review", review);
        return "user/production/form";
    }

    @PostMapping("/productions/{id}/review/edit/{reviewId}")
    public String updateProductionReview(
        @PathVariable int id,
        @PathVariable int reviewId,
        @ModelAttribute ProductionReview review,
        BindingResult br,
        Model model
    ) {
        if (br.hasErrors()) {
            Production production = productionService.getById(id);
            model.addAttribute("production", production);
            model.addAttribute("review", review);
            return "user/production/form";
        }
        review.setId(reviewId);
        review.setProduction(productionService.getById(id));
        review.setUser(getCurrentUser());
        pReviewService.update(review);
        return "redirect:/production/" + id;
    }
}
