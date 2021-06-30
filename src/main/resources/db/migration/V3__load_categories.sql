INSERT INTO public.categories (id, name, slug, price, created_at, updated_at, parent_id, tree_left, tree_right,
                               tree_level)
VALUES (1, 'Community', 'community', 0.00, NOW(), NOW(), NULL, 1, 30, 0),
       (2, 'Activities', 'community-activities', 0.00, NOW(), NOW(), 1, 2, 3, 1),
       (3, 'Artists', 'community-artists', 0.00, NOW(), NOW(), 1, 4, 5, 1),
       (4, 'Childcare', 'community-childcare', 0.00, NOW(), NOW(), 1, 6, 7, 1),
       (5, 'Classes', 'community-classes', 0.00, NOW(), NOW(), 1, 8, 9, 1),
       (6, 'Events', 'community-events', 0.00, NOW(), NOW(), 1, 10, 11, 1),
       (7, 'General', 'community-general', 0.00, NOW(), NOW(), 1, 12, 13, 1),
       (8, 'Groups', 'community-groups', 0.00, NOW(), NOW(), 1, 14, 15, 1),
       (9, 'Local news', 'community-local-news', 0.00, NOW(), NOW(), 1, 16, 17, 1),
       (10, 'Lost and found', 'community-lost-and-found', 0.00, NOW(), NOW(), 1, 18, 19, 1),
       (11, 'Musicians', 'community-musicians', 0.00, NOW(), NOW(), 1, 20, 21, 1),
       (12, 'Pets', 'community-pets', 0.00, NOW(), NOW(), 1, 22, 23, 1),
       (13, 'Politics', 'community-politics', 0.00, NOW(), NOW(), 1, 24, 25, 1),
       (14, 'Rideshare', 'community-rideshare', 0.00, NOW(), NOW(), 1, 26, 27, 1),
       (15, 'Volunteers', 'community-volunteers', 0.00, NOW(), NOW(), 1, 28, 29, 1),
       (16, 'Personals', 'personals', 0.00, NOW(), NOW(), NULL, 31, 50, 0),
       (17, 'Strictly platonic', 'personals-strictly-platonic', 0.00, NOW(), NOW(), 16, 32, 33, 1),
       (18, 'Women seeking women', 'personals-women-seeking-women', 0.00, NOW(), NOW(), 16, 34, 35, 1),
       (19, 'Women seeking men', 'personals-women-seeking-men', 0.00, NOW(), NOW(), 16, 36, 37, 1),
       (20, 'Men seeking women', 'personals-men-seeking-women', 0.00, NOW(), NOW(), 16, 38, 39, 1),
       (21, 'Men seeking men', 'personals-men-seeking-men', 0.00, NOW(), NOW(), 16, 40, 41, 1),
       (22, 'Misc romance', 'personals-misc-romance', 0.00, NOW(), NOW(), 16, 42, 43, 1),
       (23, 'Casual encounters', 'personals-casual-encounters', 0.00, NOW(), NOW(), 16, 44, 45, 1),
       (24, 'Missed connections', 'personals-missed-connections', 0.00, NOW(), NOW(), 16, 46, 47, 1),
       (25, 'Rants and raves', 'personals-rants-and-raves', 0.00, NOW(), NOW(), 16, 48, 49, 1),
       (26, 'Housing', 'housing', 0.00, NOW(), NOW(), NULL, 51, 72, 0),
       (27, 'Apartments / housing', 'housing-apartments-housing', 0.00, NOW(), NOW(), 26, 52, 53, 1),
       (28, 'Housing swap', 'housing-housing-swap', 0.00, NOW(), NOW(), 26, 54, 55, 1),
       (29, 'Housing wanted', 'housing-housing-wanted', 0.00, NOW(), NOW(), 26, 56, 57, 1),
       (30, 'Office / commercial', 'housing-office-commercial', 0.00, NOW(), NOW(), 26, 58, 59, 1),
       (31, 'Parking / storage', 'housing-parking-storage', 0.00, NOW(), NOW(), 26, 60, 61, 1),
       (32, 'Real estate for sale', 'housing-real-estate-for-sale', 0.00, NOW(), NOW(), 26, 62, 63, 1),
       (33, 'Rooms / shared', 'housing-rooms-shared', 0.00, NOW(), NOW(), 26, 64, 65, 1),
       (34, 'Rooms wanted', 'housing-rooms-wanted', 0.00, NOW(), NOW(), 26, 66, 67, 1),
       (35, 'Sublets / temporary', 'housing-sublets-temporary', 0.00, NOW(), NOW(), 26, 68, 69, 1),
       (36, 'Vacation rentals', 'housing-vacation-rentals', 0.00, NOW(), NOW(), 26, 70, 71, 1);

UPDATE categories
SET usable = TRUE
WHERE tree_level > 0;

update categories
set price = floor(random() * 10);
