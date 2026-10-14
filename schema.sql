-- =============================================================================
-- University of Ghana — Department of Computer Science
-- DCIT 204 / DCIT 308: Data Structures & Algorithms I & II
-- Joint Semester Project: Ghana Smart Food & Parcel Delivery Operations Optimizer
-- Database Schema Definition (SQLite & PostgreSQL Compatible)
-- =============================================================================

-- Table 1: locations
-- Represents delivery nodes, customer drop-offs, vendor locations, and hubs in Greater Accra.
CREATE TABLE IF NOT EXISTS locations (
    locationId      TEXT PRIMARY KEY,
    name            TEXT NOT NULL,
    area            TEXT,
    type            TEXT,
    latitude        REAL,
    longitude       REAL
);

-- Table 2: roads
-- Represents directed/undirected weighted edges between delivery locations in the road network.
CREATE TABLE IF NOT EXISTS roads (
    roadId              TEXT PRIMARY KEY,
    fromLocationId      TEXT NOT NULL,
    toLocationId        TEXT NOT NULL,
    distance            REAL,
    travelTime          REAL,
    roadConditionWeight REAL,
    FOREIGN KEY (fromLocationId) REFERENCES locations(locationId),
    FOREIGN KEY (toLocationId) REFERENCES locations(locationId)
);

-- Table 3: service_requests
-- Represents delivery orders, food parcels, and maintenance requests with urgency levels.
CREATE TABLE IF NOT EXISTS service_requests (
    requestId       TEXT PRIMARY KEY,
    source          TEXT NOT NULL,
    destination     TEXT NOT NULL,
    category        TEXT,
    urgency         INTEGER,
    timeSubmitted   TEXT,
    deadline        TEXT,
    status          TEXT
);

-- Table 4: resources
-- Represents delivery dispatch riders, vans, motorbikes, and service vehicles.
CREATE TABLE IF NOT EXISTS resources (
    resourceId          TEXT PRIMARY KEY,
    type                TEXT,
    homeLocation        TEXT,
    capacity            REAL,
    availabilityStatus  TEXT,
    FOREIGN KEY (homeLocation) REFERENCES locations(locationId)
);

-- Table 5: algorithm_runs
-- Stores empirical runtime benchmarks and input-size metrics for Big-O analysis.
CREATE TABLE IF NOT EXISTS algorithm_runs (
    runId           TEXT PRIMARY KEY,
    algorithmName   TEXT NOT NULL,
    inputSize       INTEGER,
    timeNs          BIGINT,
    memoryKb        REAL,
    dateRun         TEXT
);

-- Table 6: audit_events
-- Stores stack-based undo/audit operations, transaction logs, and key system events.
CREATE TABLE IF NOT EXISTS audit_events (
    eventId         TEXT PRIMARY KEY,
    eventType       TEXT NOT NULL,
    description     TEXT,
    timestamp       TEXT
);

-- Indexes for performance optimization on relational queries and joins
CREATE INDEX IF NOT EXISTS idx_roads_from ON roads(fromLocationId);
CREATE INDEX IF NOT EXISTS idx_roads_to ON roads(toLocationId);
CREATE INDEX IF NOT EXISTS idx_requests_urgency ON service_requests(urgency);
CREATE INDEX IF NOT EXISTS idx_requests_status ON service_requests(status);
CREATE INDEX IF NOT EXISTS idx_algo_name_size ON algorithm_runs(algorithmName, inputSize);
