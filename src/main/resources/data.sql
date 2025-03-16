INSERT INTO store (name, code, latitude, longitude, version)
SELECT 'Ataşehir', 'ATA34', 40.9923307, 29.1244229, 0
    WHERE NOT EXISTS (SELECT 1 FROM store WHERE code = 'ATA34');

INSERT INTO store (name, code, latitude, longitude, version)
SELECT 'Novada', 'NOV34', 40.986106, 29.1161293, 0
    WHERE NOT EXISTS (SELECT 1 FROM store WHERE code = 'NOV34');

INSERT INTO store (name, code, latitude, longitude, version)
SELECT 'Beylikdüzü', 'BEY34', 41.0066851, 28.6552262, 0
    WHERE NOT EXISTS (SELECT 1 FROM store WHERE code = 'BEY34');

INSERT INTO store (name, code, latitude, longitude, version)
SELECT 'Ortaköy', 'ORT34', 41.055783, 29.0210292, 0
    WHERE NOT EXISTS (SELECT 1 FROM store WHERE code = 'ORT34');

INSERT INTO store (name, code, latitude, longitude, version)
SELECT 'Caddebostan', 'CAD34', 40.9632463, 29.0630908, 0
    WHERE NOT EXISTS (SELECT 1 FROM store WHERE code = 'CAD34');

------------------------------------------------------------------------------------------------------------------------

INSERT INTO courier (name, identity_number, version)
SELECT 'Sacid PAK', '12345678901', 0
    WHERE NOT EXISTS (SELECT 1 FROM courier WHERE identity_number = '12345678901');

INSERT INTO courier (name, identity_number, version)
SELECT 'Efe PAK', '22345678901', 0
    WHERE NOT EXISTS (SELECT 1 FROM courier WHERE identity_number = '22345678901');
