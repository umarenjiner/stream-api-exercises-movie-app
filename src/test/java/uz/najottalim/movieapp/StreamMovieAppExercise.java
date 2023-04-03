package uz.najottalim.movieapp;


import org.junit.jupiter.api.*;

import lombok.extern.slf4j.Slf4j;
import uz.najottalim.movieapp.models.Director;
import uz.najottalim.movieapp.models.Genre;
import uz.najottalim.movieapp.models.Movie;
import uz.najottalim.movieapp.repos.DirectorRepo;
import uz.najottalim.movieapp.repos.GenreRepo;
import uz.najottalim.movieapp.repos.MovieRepo;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class StreamMovieAppExercise {

    private DirectorRepo directorRepo;
    private GenreRepo genreRepo;
    private MovieRepo movieRepo;

    @BeforeEach
    void setUp() {
        directorRepo = new DirectorRepo();
        genreRepo = new GenreRepo();
        movieRepo = new MovieRepo();
    }

    @Test
    @DisplayName("Hammasini ko'rish")
    public void printAll(long solution2) {
        // System.out.println o'rniga shuni ishlatingla
        log.info("Movies:");
        movieRepo.findAll()
                .forEach(p -> log.info(p.toString() + '\n'));
        log.info("Directors:");
        directorRepo.findAll()
                .forEach(p -> log.info(p.toString() + '\n'));
        log.info("Genres:");
        genreRepo.findAll()
                .forEach(p -> log.info(p.toString() + '\n'));
    }

    @Test
    @DisplayName("Janri 'Drama' yoki 'Komediya' bo'lgan kinolarni toping")
    public void exercise1() {
        List<Movie> movies = movieRepo.findAll()
                .stream()
                .filter(movie ->
                        movie.getGenres()
                                .stream()
                                .anyMatch(genre ->
                                        genre.getName().equalsIgnoreCase("Drama")
                                                || genre.getName().equalsIgnoreCase("Komediya")))
                .collect(Collectors.toList());
        movies.forEach(movie -> {
            System.out.println(movie.getTitle() +
                    " -> janrlari: " +
                    movie.getGenres()
                            .stream()
                            .map(Genre::getName)
                            .collect(Collectors.toList()));
            System.out.println(movies);
        });
    }

    @Test
    @DisplayName("Har bitta rejissorning olgan kinolar sonini chiqaring")
    public void exercise2() {
        var solution2 = directorRepo.findAll()
                .stream()
                .flatMap(director -> director.getMovies().stream())
                .count();
        System.out.println(solution2);


    }

    @Test
    @DisplayName("Eng oldin olingan kinoni chiqaring")
    public void exercise8() {
        var solution8 = movieRepo.findAll().stream()
                .sorted((Comparator.comparing(Movie::getYear)))
                .limit(1).collect(Collectors.toList());
        System.out.println("Eng eski kino: " + solution8);
    }

    @Test
    @DisplayName("2004 chi yilda kinolaga sarflangan umumiy summani chiqaring")
    public void exercise9() {
        var solution9 = movieRepo.findAll()
                .stream()

                .filter(movie -> movie.getYear() == 2004)
                .mapToDouble(Movie::getSpending).sum();
        System.out.println(solution9);
    }

    @Test
    @DisplayName("har bir yilda olingan kinolarni o'rtacha reytingini chiqaring")
    public void exercise10() {
        var solution10 = movieRepo.findAll()
                .stream()
                .mapToDouble(Movie::getRating).summaryStatistics();
        System.out.println("O'rtacha reyting : " + solution10);

    }

    @Test
    @DisplayName("Janri 'Drama' bo'lgan eng tarixda suratga olingan kinoni chiqaring")
    public void exercise11() {
        var solution = movieRepo.findAll()
                .stream()
                .filter(movie -> movie.getGenres().stream()
                        .anyMatch(genre -> genre.getName().equalsIgnoreCase("Drama")))
                .sorted(Comparator.comparing(Movie::getYear)).limit(1)
                .collect(Collectors.toList());
        System.out.println("Janri 'Drama' bo'lgan eng tarixda suratga olingan kino: " + solution);
    }

    @Test
    @DisplayName("Qaysi janrdagi kinolar eng ko'p oscar olganligini chiqaring")
    public void exercise12() {
        var solution12 = movieRepo.findAll().stream()
                .sorted(Comparator.comparing(Movie::isWinOscar))
                .flatMap(movie -> movie.getGenres().stream())
                .collect(Collectors.toList());
        System.out.println(solution12);

    }

    @Test
    @DisplayName("Har bir rejissorni olgan kinolarining janrini soni chiqaring" +
            "Misol uchun: " +
            "Aziz Aliev suratga olgan" +
            "Komediya    : 2\n" +
            "Drama       : 5\n" +
            "Romantika   : 2")
    public void exercise3() {
        var solution3 = directorRepo.findAll().stream()
                .flatMap(director -> director.getMovies().stream())
                .flatMap(movie -> movie.getGenres().stream())
                .count();
        System.out.println(solution3);
//        Map<String, String> result = productRepo.findAll()
//                .stream()
//                .collect(
//                        Collectors.groupingBy(
//                                Product::getCategory,
//                                Collectors.collectingAndThen(
//                                        Collectors.maxBy(Comparator.comparingDouble(Product::getPrice)),
//                                        optionalProduct -> optionalProduct.map(Product::getName).orElse(null)
//                                )
//                        ));

    }

    @Test
    @DisplayName("2004 chi yilda chiqqan kinolar orasida eng ko'p pul sarflanganini chiqaring")
    public void exercise4() {
        var solution4 = movieRepo.findAll()
                .stream()
                .filter(movie -> movie.getYear()==2004)
                .sorted(Comparator.comparing(Movie::getSpending).reversed()).limit(1)
                .collect(Collectors.toList());
        System.out.println(solution4);
        //spending=1.22342345323E8
        //spending=65423.3256
    }

    @Test
    @DisplayName("har bitta rejissor olgan kinolarining o'rtacha ratingini chiqaring" +
            "Misol uchun:" +
            "Sardor Muhammadaliev: 2.23" +
            "Akrom Aliev: 2.33")
    public void exercise5() {

    }

    @Test
    @DisplayName("Rejissolarni umumiy kinolari uchun olgan oskarlari soni bo'yicha saralab chiqaring")
    public void exercise6() {
        var solution6 = directorRepo.findAll().stream()
                .flatMap(director -> director.getMovies().stream())
                .sorted(Comparator.comparing(Movie::isWinOscar).reversed()).collect(Collectors.toList());
        System.out.println(solution6);
    }

    @Test
    @DisplayName("2004 yilda olingan Komediya kinolariga ketgan umumiy summani chiqaring")
    public void exercise7() {
        var solution7 = movieRepo.findAll().stream()
                .filter(movie -> movie.getYear()==2004)
                .filter(movie -> movie.getGenres().stream()
                        .anyMatch(genre -> genre.getName().equalsIgnoreCase("Komediya")))
                .mapToDouble(Movie::getSpending)
                .sum();
        System.out.println("Spending: " + solution7);
    }


    @Test
    @DisplayName("Kinolarni chiqgan yili bo'yicha guruhlab, ratingi bo'yicha saralab toping")
    public void exercise13() {


    }

    @Test
    @DisplayName("Har bitta rejiossor qaysi janrda o'rtacha reytingi eng ko'p ekanligini chiqaring")
    public void exercise14() {

    }

    @Test
    @DisplayName("Eng kam kinolarga pul sarflagan rejissorni chiqaring")
    public void exercise15() {
        var solution15 = directorRepo.findAll().stream()
                .flatMap(director -> director.getMovies().stream())
                .sorted(Comparator.comparing(Movie::getSpending))
                .limit(1).collect(Collectors.toList());
        System.out.println("Eng kam kinolarga pul sarflagan rejissor: " + solution15);
        //spending=65423.3256
    }

    @Test
    @DisplayName("Komediya kinolarini, 2000 chi yildan keyin olinganlarga ketgan narxni DoubleSummaryStatisticasini chiqaring")
    public void exercise16() {
        var solution16 = movieRepo.findAll().stream()
                .filter(movie -> movie.getYear() > 2000)
                .filter(movie -> movie.getGenres().stream().anyMatch(genre -> genre.getName()
                        .contentEquals("Komediya")))
                .mapToDouble(Movie::getSpending).summaryStatistics();
        System.out.println(solution16);
    }

    @Test
    @DisplayName("Qaysi kinoni eng ko'p rejissorlar birgalikda olishgan va ratingi eng baland chiqqan")
    public void exercise17() {

    }

    @Test
    @DisplayName("Har bir janrdagi kino nomlari umumiy nechta so'zdan iborat")
    public void exercise18() {

    }

    @Test
    @DisplayName("Har bir asrda olingan kinolarni o'rtacha reytingini chiqaring")
    public void exercise19() {

    }

    @Test
    @DisplayName("Ismi A harfi bilan boshlanadigan rejissorlarni olgan kinolarini o'rtacha ratingi bo'yicha saralab chiqaring")
    public void exercise20() {

    }
}
