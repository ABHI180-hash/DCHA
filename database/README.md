# Digital Culture Heritage Archive (DCHA) Database

This directory contains the database schema and seed data for the DCHA platform.

## Files

- `schema.sql`: Contains the complete MySQL schema definition for all 24 tables.
- `seed.sql`: Contains comprehensive, culturally accurate seed data for all 28 states and 8 Union Territories of India, including users, heritage sites, festivals, foods, traditional dresses, traditions, languages, dances, music, arts, and more.

## Setup Instructions

1. **Install MySQL**
   Ensure that MySQL server is installed and running on your system.

2. **Create the Database**
   Open your MySQL command line or a tool like MySQL Workbench and create the database:
   ```sql
   CREATE DATABASE dcha_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   USE dcha_db;
   ```

3. **Import Schema**
   Import the schema to create all tables and relationships:
   ```bash
   mysql -u yourusername -p dcha_db < schema.sql
   ```

4. **Import Seed Data**
   The seed data file is quite large as it encompasses the entire cultural heritage archive data for all states and UTs.
   ```bash
   mysql -u yourusername -p dcha_db < seed.sql
   ```

   *(Note: Depending on your MySQL configuration, you may need to increase the `max_allowed_packet` size in your `my.cnf` or `my.ini` if you encounter packet size errors during the import of the large `seed.sql` file.)*

## Schema Highlights

- **Users**: Supports 'user', 'admin', and 'curator' roles.
- **States**: The central table linking all cultural artifacts by state/UT. Includes full geographic and cultural overview.
- **Cultural Entities**: Tables for `heritage_sites`, `festivals`, `foods`, `traditional_dresses`, `traditions`, `languages`, `dances`, `music`, `arts_crafts`, and `folk_stories`.
- **Media & Curation**: `gallery_items`, `museum_artifacts`, `audio_items`, and `timeline_events`.
- **User Interactions**: `favorites`, `collections`, `contributions` (with review status).
- **Other**: `heritage_risks`, `cultural_events`, `newsletter_subscribers`, and `contact_messages`.
