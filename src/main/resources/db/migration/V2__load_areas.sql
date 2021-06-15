INSERT INTO areas (id, name, slug, created_at, updated_at, parent_id, tree_left, tree_right, tree_level)
VALUES (1, 'Estonia', 'estonia', NOW(), NOW(), NULL, 1, 30, 0),
       (2, 'Harjumaa', 'estonia-harjumaa', NOW(), NOW(), 1, 2, 13, 1),
       (3, 'Tallinn', 'harjumaa-tallinn', NOW(), NOW(), 2, 3, 4, 2),
       (4, 'Maardu', 'harjumaa-maardu', NOW(), NOW(), 2, 5, 6, 2),
       (5, 'Keila', 'harjumaa-keila', NOW(), NOW(), 2, 7, 8, 2),
       (6, 'Haabneeme', 'harjumaa-haabneeme', NOW(), NOW(), 2, 9, 10, 2),
       (7, 'Saue', 'harjumaa-saue', NOW(), NOW(), 2, 11, 12, 2),
       (8, 'Tartumaa', 'estonia-tartumaa', NOW(), NOW(), 1, 14, 21, 1),
       (9, 'Tartu', 'tartumaa-tartu', NOW(), NOW(), 8, 15, 16, 2),
       (10, 'Elva', 'tartumaa-elva', NOW(), NOW(), 8, 17, 18, 2),
       (11, 'Kambja', 'tartumaa-kambja', NOW(), NOW(), 8, 19, 20, 2),
       (12, 'Ida-Virumaa', 'estonia-ida-virumaa', NOW(), NOW(), 1, 22, 29, 1),
       (13, 'Narva', 'ida-virumaa-narva', NOW(), NOW(), 12, 23, 24, 2),
       (14, 'Kohtla-Järve', 'ida-virumaa-kohtla-jaerve', NOW(), NOW(), 12, 25, 26, 2),
       (15, 'Sillamäe', 'ida-virumaa-sillamaee', NOW(), NOW(), 12, 27, 28, 2),
       (16, 'USA', 'usa', NOW(), NOW(), NULL, 31, 146, 0),
       (17, 'Alabama', 'usa-alabama', NOW(), NOW(), 16, 32, 49, 1),
       (18, 'Auburn', 'alabama-auburn', NOW(), NOW(), 17, 33, 34, 2),
       (19, 'Birmingham', 'alabama-birmingham', NOW(), NOW(), 17, 35, 36, 2),
       (20, 'Dothan', 'alabama-dothan', NOW(), NOW(), 17, 37, 38, 2),
       (21, 'Florence / Muscle shoals', 'alabama-florence-muscle-shoals', NOW(), NOW(), 17, 39, 40, 2),
       (22, 'Huntsville / Decatur', 'alabama-huntsville-decatur', NOW(), NOW(), 17, 41, 42, 2),
       (23, 'Mobile', 'alabama-mobile', NOW(), NOW(), 17, 43, 44, 2),
       (24, 'Montgomery', 'alabama-montgomery', NOW(), NOW(), 17, 45, 46, 2),
       (25, 'Tuscaloosa', 'alabama-tuscaloosa', NOW(), NOW(), 17, 47, 48, 2),
       (26, 'Alaska', 'usa-alaska', NOW(), NOW(), 16, 50, 59, 1),
       (27, 'Anchorage / Mat-su', 'alaska-anchorage-mat-su', NOW(), NOW(), 26, 51, 52, 2),
       (28, 'Fairbanks', 'alaska-fairbanks', NOW(), NOW(), 26, 53, 54, 2),
       (29, 'Kenai Peninsula', 'alaska-kenai-peninsula', NOW(), NOW(), 26, 55, 56, 2),
       (30, 'Southeast Alaska', 'alaska-southeast-alaska', NOW(), NOW(), 26, 57, 58, 2),
       (31, 'Arizona', 'usa-arizona', NOW(), NOW(), 16, 60, 77, 1),
       (32, 'Flagstaff / Sedona', 'arizona-flagstaff-sedona', NOW(), NOW(), 31, 61, 62, 2),
       (33, 'Mohave County', 'arizona-mohave-county', NOW(), NOW(), 31, 63, 64, 2),
       (34, 'Phoenix', 'arizona-phoenix', NOW(), NOW(), 31, 65, 66, 2),
       (35, 'Prescott', 'arizona-prescott', NOW(), NOW(), 31, 67, 68, 2),
       (36, 'Show Low', 'arizona-show-low', NOW(), NOW(), 31, 69, 70, 2),
       (37, 'Sierra Vista', 'arizona-sierra-vista', NOW(), NOW(), 31, 71, 72, 2),
       (38, 'Tucson', 'arizona-tucson', NOW(), NOW(), 31, 73, 74, 2),
       (39, 'Yuma', 'arizona-yuma', NOW(), NOW(), 31, 75, 76, 2),
       (40, 'Arkansas', 'usa-arkansas', NOW(), NOW(), 16, 78, 89, 1),
       (41, 'Fayetteville', 'arkansas-fayetteville', NOW(), NOW(), 40, 79, 80, 2),
       (42, 'Fort Smith', 'arkansas-fort-smith', NOW(), NOW(), 40, 81, 82, 2),
       (43, 'Jonesboro', 'arkansas-jonesboro', NOW(), NOW(), 40, 83, 84, 2),
       (44, 'Little Rock', 'arkansas-little-rock', NOW(), NOW(), 40, 85, 86, 2),
       (45, 'Texarkana', 'arkansas-texarkana', NOW(), NOW(), 40, 87, 88, 2),
       (46, 'California', 'usa-california', NOW(), NOW(), 16, 90, 145, 1),
       (47, 'Bakersfield', 'california-bakersfield', NOW(), NOW(), 46, 91, 92, 2),
       (48, 'Chico', 'california-chico', NOW(), NOW(), 46, 93, 94, 2),
       (49, 'Fresno / Madera', 'california-fresno-madera', NOW(), NOW(), 46, 95, 96, 2),
       (50, 'Gold Country', 'california-gold-country', NOW(), NOW(), 46, 97, 98, 2),
       (51, 'Hanford-Corcoran', 'california-hanford-corcoran', NOW(), NOW(), 46, 99, 100, 2),
       (52, 'Humboldt County', 'california-humboldt-county', NOW(), NOW(), 46, 101, 102, 2),
       (53, 'Inland Empire', 'california-inland-empire', NOW(), NOW(), 46, 103, 104, 2),
       (54, 'Los Angeles', 'california-los-angeles', NOW(), NOW(), 46, 105, 106, 2),
       (55, 'Mendocino County', 'california-mendocino-county', NOW(), NOW(), 46, 107, 108, 2),
       (56, 'Merced', 'california-merced', NOW(), NOW(), 46, 109, 110, 2),
       (57, 'Modesto', 'california-modesto', NOW(), NOW(), 46, 111, 112, 2),
       (58, 'Monterey Bay', 'california-monterey-bay', NOW(), NOW(), 46, 113, 114, 2),
       (59, 'Orange County', 'california-orange-county', NOW(), NOW(), 46, 115, 116, 2),
       (60, 'Palm Springs', 'california-palm-springs', NOW(), NOW(), 46, 117, 118, 2),
       (61, 'Redding', 'california-redding', NOW(), NOW(), 46, 119, 120, 2),
       (62, 'Sacramento', 'california-sacramento', NOW(), NOW(), 46, 121, 122, 2),
       (63, 'San Diego', 'california-san-diego', NOW(), NOW(), 46, 123, 124, 2),
       (64, 'San Francisco Bay Area', 'california-san-francisco-bay-area', NOW(), NOW(), 46, 125, 126, 2),
       (65, 'San Luis Obispo', 'california-san-luis-obispo', NOW(), NOW(), 46, 127, 128, 2),
       (66, 'Santa Barbara', 'california-santa-barbara', NOW(), NOW(), 46, 129, 130, 2),
       (67, 'Santa Maria', 'california-santa-maria', NOW(), NOW(), 46, 131, 132, 2),
       (68, 'Siskiyou County', 'california-siskiyou-county', NOW(), NOW(), 46, 133, 134, 2),
       (69, 'Stockton', 'california-stockton', NOW(), NOW(), 46, 135, 136, 2),
       (70, 'Susanville', 'california-susanville', NOW(), NOW(), 46, 137, 138, 2),
       (71, 'Ventura County', 'california-ventura-county', NOW(), NOW(), 46, 139, 140, 2),
       (72, 'Visalia-Tulare', 'california-visalia-tulare', NOW(), NOW(), 46, 141, 142, 2),
       (73, 'Yuba-Sutter', 'california-yuba-sutter', NOW(), NOW(), 46, 143, 144, 2);
