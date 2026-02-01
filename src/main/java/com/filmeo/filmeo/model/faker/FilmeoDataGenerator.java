package com.filmeo.filmeo.model.faker;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import net.datafaker.Faker;

/**
 * Data generator for Filmeo database using Datafaker
 * Run with: javac -cp ".:datafaker-2.1.0.jar" FilmeoDataGenerator.java
 *           java -cp ".:datafaker-2.1.0.jar:mariadb-java-client.jar" FilmeoDataGenerator
 */
public class FilmeoDataGenerator {

    private static final Faker faker = new Faker(
        Locale.forLanguageTag("en-US")
    );
    private static Connection conn;

    // Configuration
    private static final int NUM_ARTISTS = 100;
    private static final int NUM_PRODUCTIONS = 50;
    private static final int NUM_USERS = 30;
    private static final int NUM_GENRES = 15;
    private static final int NUM_PLATFORMS = 8;
    private static final int MAX_CAST_PER_PRODUCTION = 15;
    private static final int MAX_GENRES_PER_PRODUCTION = 3;

    private static List<Integer> countryIds = new ArrayList<>();
    private static List<Integer> artistIds = new ArrayList<>();
    private static List<Integer> productionIds = new ArrayList<>();
    private static List<Integer> userIds = new ArrayList<>();
    private static List<Integer> genreIds = new ArrayList<>();
    private static List<Integer> platformIds = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        // Database connection
        String url = "jdbc:mariadb://localhost:3306/filmeo";
        String user = "root";
        String password = "";

        conn = DriverManager.getConnection(url, user, password);
        conn.setAutoCommit(false);

        try {
            System.out.println("🎬 Starting Filmeo data generation...\n");

            loadExistingCountries();
            generateGenres();
            generateStreamingPlatforms();
            generateArtists();
            generateProductions();
            generateUsers();
            generateCasting();
            generateProductionGenres();
            generateAvailabilities();
            generateProductionReviews();
            generateArtistReviews();
            generateWatchLists();

            conn.commit();
            System.out.println("\n✅ Data generation completed successfully!");
        } catch (Exception e) {
            conn.rollback();
            System.err.println("❌ Error during generation: " + e.getMessage());
            e.printStackTrace();
        } finally {
            conn.close();
        }
    }

    private static void loadExistingCountries() throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT id FROM country");
        while (rs.next()) {
            countryIds.add(rs.getInt("id"));
        }
        System.out.println("📍 Loaded " + countryIds.size() + " countries");
    }

    private static void generateGenres() throws SQLException {
        String[] genres = {
            "Action",
            "Comedy",
            "Drama",
            "Thriller",
            "Horror",
            "Science Fiction",
            "Romance",
            "Documentary",
            "Animation",
            "Musical",
            "Crime",
            "Fantasy",
            "Mystery",
            "Adventure",
            "War",
        };

        PreparedStatement pstmt = conn.prepareStatement(
            "INSERT INTO genre (name) VALUES (?)",
            Statement.RETURN_GENERATED_KEYS
        );

        for (String genre : genres) {
            pstmt.setString(1, genre);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                genreIds.add(rs.getInt(1));
            }
        }
        System.out.println("🎭 Generated " + genreIds.size() + " genres");
    }

    private static void generateStreamingPlatforms() throws SQLException {
        String[][] platforms = {
            {
                "Netflix",
                "https://www.netflix.com",
                "https://logo.clearbit.com/netflix.com",
            },
            {
                "Amazon Prime Video",
                "https://www.primevideo.com",
                "https://logo.clearbit.com/primevideo.com",
            },
            {
                "Disney+",
                "https://www.disneyplus.com",
                "https://logo.clearbit.com/disneyplus.com",
            },
            {
                "HBO Max",
                "https://www.hbomax.com",
                "https://logo.clearbit.com/hbomax.com",
            },
            {
                "Apple TV+",
                "https://tv.apple.com",
                "https://logo.clearbit.com/apple.com",
            },
            {
                "Paramount+",
                "https://www.paramountplus.com",
                "https://logo.clearbit.com/paramountplus.com",
            },
            {
                "Hulu",
                "https://www.hulu.com",
                "https://logo.clearbit.com/hulu.com",
            },
            {
                "Peacock",
                "https://www.peacocktv.com",
                "https://logo.clearbit.com/peacocktv.com",
            },
        };

        PreparedStatement pstmt = conn.prepareStatement(
            "INSERT INTO streaming_platform (name, webaddress, logo_url) VALUES (?, ?, ?)",
            Statement.RETURN_GENERATED_KEYS
        );

        for (String[] platform : platforms) {
            pstmt.setString(1, platform[0]);
            pstmt.setString(2, platform[1]);
            pstmt.setString(3, platform[2]);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                platformIds.add(rs.getInt(1));
            }
        }
        System.out.println(
            "📺 Generated " + platformIds.size() + " streaming platforms"
        );
    }

    private static void generateArtists() throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(
            "INSERT INTO artist (nationality_id, birth_date, death_date, firstname, gender, lastname, picture_url) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)",
            Statement.RETURN_GENERATED_KEYS
        );

        for (int i = 0; i < NUM_ARTISTS; i++) {
            boolean isMale = faker.random().nextBoolean();
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();

            LocalDate birthDate = LocalDate.of(
                faker.number().numberBetween(1940, 2000),
                faker.number().numberBetween(1, 13),
                faker.number().numberBetween(1, 29)
            );

            // 5% chance of being deceased
            LocalDateTime deathDate = null;
            if (
                faker.number().numberBetween(1, 101) <= 5 &&
                birthDate.isBefore(LocalDate.of(1990, 1, 1))
            ) {
                deathDate = birthDate
                    .plusYears(faker.number().numberBetween(40, 80))
                    .atStartOfDay();
            }

            pstmt.setInt(
                1,
                countryIds.get(faker.random().nextInt(countryIds.size()))
            );
            pstmt.setTimestamp(2, Timestamp.valueOf(birthDate.atStartOfDay()));
            pstmt.setTimestamp(
                3,
                deathDate != null ? Timestamp.valueOf(deathDate) : null
            );
            pstmt.setString(4, firstName);
            pstmt.setString(5, isMale ? "Male" : "Female");
            pstmt.setString(6, lastName);
            pstmt.setString(
                7,
                "https://i.pravatar.cc/300?u=" + UUID.randomUUID()
            );

            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                artistIds.add(rs.getInt(1));
            }
        }
        System.out.println("🎬 Generated " + artistIds.size() + " artists");
    }

    private static void generateProductions() throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(
            "INSERT INTO production (director_id, nationality_id, poster_url, title, type) " +
                "VALUES (?, ?, ?, ?, ?)",
            Statement.RETURN_GENERATED_KEYS
        );

        String[] types = { "Movie", "Series" };
        Set<String> usedTitles = new HashSet<>();

        for (int i = 0; i < NUM_PRODUCTIONS; i++) {
            String title;
            do {
                title =
                    faker.book().title() +
                    (faker.random().nextBoolean()
                        ? ""
                        : ": " + faker.color().name());
            } while (usedTitles.contains(title));
            usedTitles.add(title);

            String type = types[faker.random().nextInt(types.length)];

            pstmt.setInt(
                1,
                artistIds.get(faker.random().nextInt(artistIds.size()))
            );
            pstmt.setInt(
                2,
                countryIds.get(faker.random().nextInt(countryIds.size()))
            );
            pstmt.setString(
                3,
                "https://picsum.photos/seed/" + UUID.randomUUID() + "/400/600"
            );
            pstmt.setString(4, title);
            pstmt.setString(5, type);

            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                productionIds.add(rs.getInt(1));
            }
        }
        System.out.println(
            "🎥 Generated " + productionIds.size() + " productions"
        );
    }

    private static void generateUsers() throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(
            "INSERT INTO user (nationality_id, email, password, username, roles) " +
                "VALUES (?, ?, ?, ?, ?)",
            Statement.RETURN_GENERATED_KEYS
        );

        // BCrypt hash for "password123"
        String hashedPassword =
            "$2a$10$vtZCeo5NF0SsOiUFyBouoO4Jt9f6SnP2JZK/0QcbxcwaA2PVa9qqW";
        Set<String> usedUsernames = new HashSet<>();
        Set<String> usedEmails = new HashSet<>();

        for (int i = 0; i < NUM_USERS; i++) {
            String username;
            do {
                username = faker.name().firstName();
            } while (usedUsernames.contains(username));
            usedUsernames.add(username);

            String email;
            do {
                email = faker.internet().emailAddress().toLowerCase();
            } while (usedEmails.contains(email));
            usedEmails.add(email);

            boolean isAdmin = faker.number().numberBetween(1, 101) <= 10; // 10% admins
            String roles = isAdmin ? "[\"ADMIN\", \"USER\"]" : "[\"USER\"]";

            pstmt.setInt(
                1,
                faker.random().nextBoolean()
                    ? countryIds.get(faker.random().nextInt(countryIds.size()))
                    : null
            );
            pstmt.setString(2, email);
            pstmt.setString(3, hashedPassword);
            pstmt.setString(4, username);
            pstmt.setString(5, roles);

            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                userIds.add(rs.getInt(1));
            }
        }
        System.out.println("👥 Generated " + userIds.size() + " users");
    }

    private static void generateCasting() throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(
            "INSERT INTO casting (artist_id, production_id) VALUES (?, ?)"
        );

        int totalCasting = 0;
        for (Integer productionId : productionIds) {
            int numActors = faker
                .number()
                .numberBetween(3, MAX_CAST_PER_PRODUCTION + 1);
            Set<Integer> usedArtists = new HashSet<>();

            for (int i = 0; i < numActors; i++) {
                Integer artistId;
                do {
                    artistId = artistIds.get(
                        faker.random().nextInt(artistIds.size())
                    );
                } while (usedArtists.contains(artistId));
                usedArtists.add(artistId);

                pstmt.setInt(1, artistId);
                pstmt.setInt(2, productionId);
                pstmt.executeUpdate();
                totalCasting++;
            }
        }
        System.out.println("🎭 Generated " + totalCasting + " casting entries");
    }

    private static void generateProductionGenres() throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(
            "INSERT INTO production_genre (genre_id, production_id) VALUES (?, ?)"
        );

        int totalGenres = 0;
        for (Integer productionId : productionIds) {
            int numGenres = faker
                .number()
                .numberBetween(1, MAX_GENRES_PER_PRODUCTION + 1);
            Set<Integer> usedGenres = new HashSet<>();

            for (int i = 0; i < numGenres; i++) {
                Integer genreId;
                do {
                    genreId = genreIds.get(
                        faker.random().nextInt(genreIds.size())
                    );
                } while (usedGenres.contains(genreId));
                usedGenres.add(genreId);

                pstmt.setInt(1, genreId);
                pstmt.setInt(2, productionId);
                pstmt.executeUpdate();
                totalGenres++;
            }
        }
        System.out.println(
            "🏷️  Generated " + totalGenres + " production-genre associations"
        );
    }

    private static void generateAvailabilities() throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(
            "INSERT INTO availability (expiraton_date, production_id, streaming_platform_id) " +
                "VALUES (?, ?, ?)"
        );

        int totalAvailabilities = 0;
        for (Integer productionId : productionIds) {
            // 70% chance of being available on streaming
            if (faker.number().numberBetween(1, 101) <= 70) {
                int numPlatforms = faker.number().numberBetween(1, 4);
                Set<Integer> usedPlatforms = new HashSet<>();

                for (int i = 0; i < numPlatforms; i++) {
                    Integer platformId;
                    do {
                        platformId = platformIds.get(
                            faker.random().nextInt(platformIds.size())
                        );
                    } while (usedPlatforms.contains(platformId));
                    usedPlatforms.add(platformId);

                    // Expiration date between now and 2 years
                    LocalDate expirationDate = LocalDate.now().plusDays(
                        faker.number().numberBetween(30, 730)
                    );

                    pstmt.setDate(1, Date.valueOf(expirationDate));
                    pstmt.setInt(2, productionId);
                    pstmt.setInt(3, platformId);
                    pstmt.executeUpdate();
                    totalAvailabilities++;
                }
            }
        }
        System.out.println(
            "📡 Generated " + totalAvailabilities + " availability entries"
        );
    }

    private static void generateProductionReviews() throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(
            "INSERT INTO production_review (production_id, user_id, comment, note) " +
                "VALUES (?, ?, ?, ?)"
        );

        int totalReviews = 0;
        for (Integer userId : userIds) {
            int numReviews = faker.number().numberBetween(0, 8);
            Set<Integer> reviewedProductions = new HashSet<>();

            for (int i = 0; i < numReviews; i++) {
                Integer productionId;
                do {
                    productionId = productionIds.get(
                        faker.random().nextInt(productionIds.size())
                    );
                } while (reviewedProductions.contains(productionId));
                reviewedProductions.add(productionId);

                String comment = faker.random().nextBoolean()
                    ? faker
                          .lorem()
                          .sentence(faker.number().numberBetween(10, 30))
                    : null;
                String note = String.valueOf(
                    faker.number().numberBetween(1, 11)
                ); // 1-10

                pstmt.setInt(1, productionId);
                pstmt.setInt(2, userId);
                pstmt.setString(3, comment);
                pstmt.setString(4, note);
                pstmt.executeUpdate();
                totalReviews++;
            }
        }
        System.out.println(
            "⭐ Generated " + totalReviews + " production reviews"
        );
    }

    private static void generateArtistReviews() throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(
            "INSERT INTO artist_review (artist_id, user_id, comment, note) " +
                "VALUES (?, ?, ?, ?)"
        );

        int totalReviews = 0;
        for (Integer userId : userIds) {
            int numReviews = faker.number().numberBetween(0, 5);
            Set<Integer> reviewedArtists = new HashSet<>();

            for (int i = 0; i < numReviews; i++) {
                Integer artistId;
                do {
                    artistId = artistIds.get(
                        faker.random().nextInt(artistIds.size())
                    );
                } while (reviewedArtists.contains(artistId));
                reviewedArtists.add(artistId);

                String comment = faker.random().nextBoolean()
                    ? faker
                          .lorem()
                          .sentence(faker.number().numberBetween(10, 20))
                    : null;
                String note = String.valueOf(
                    faker.number().numberBetween(1, 11)
                ); // 1-10

                pstmt.setInt(1, artistId);
                pstmt.setInt(2, userId);
                pstmt.setString(3, comment);
                pstmt.setString(4, note);
                pstmt.executeUpdate();
                totalReviews++;
            }
        }
        System.out.println("🌟 Generated " + totalReviews + " artist reviews");
    }

    private static void generateWatchLists() throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(
            "INSERT INTO watch_list (production_id, user_id) VALUES (?, ?)"
        );

        int totalWatchList = 0;
        for (Integer userId : userIds) {
            int numWatchList = faker.number().numberBetween(0, 15);
            Set<Integer> watchedProductions = new HashSet<>();

            for (int i = 0; i < numWatchList; i++) {
                Integer productionId;
                do {
                    productionId = productionIds.get(
                        faker.random().nextInt(productionIds.size())
                    );
                } while (watchedProductions.contains(productionId));
                watchedProductions.add(productionId);

                pstmt.setInt(1, productionId);
                pstmt.setInt(2, userId);
                pstmt.executeUpdate();
                totalWatchList++;
            }
        }
        System.out.println(
            "📋 Generated " + totalWatchList + " watch list entries"
        );
    }
}
