INSERT INTO meeting_room (name,capacity,location,commissioned) VALUES ( 'Amaze',3,'27th Floor',true);
INSERT INTO meeting_room (name,capacity,location,commissioned) VALUES ( 'Beauty',7,'27th Floor',true);
INSERT INTO meeting_room (name,capacity,location,commissioned) VALUES ( 'Inspire',12,'27th Floor',true);
INSERT INTO meeting_room (name,capacity,location,commissioned) VALUES ( 'Strive',20,'27th Floor',true);


INSERT INTO room_maintenance (room_id,begin_time,end_time) VALUES ( select id from meeting_room where name='Amaze','9:00:00','9:15:00');
INSERT INTO room_maintenance (room_id,begin_time,end_time) VALUES ( select id from meeting_room where name='Amaze','13:00:00','13:15:00');
INSERT INTO room_maintenance (room_id,begin_time,end_time) VALUES ( select id from meeting_room where name='Amaze','17:00:00','17:15:00');

INSERT INTO room_maintenance (room_id,begin_time,end_time) VALUES ( select id from meeting_room where name='Beauty','9:00:00','9:15:00');
INSERT INTO room_maintenance (room_id,begin_time,end_time) VALUES ( select id from meeting_room where name='Beauty','13:00:00','13:15:00');
INSERT INTO room_maintenance (room_id,begin_time,end_time) VALUES ( select id from meeting_room where name='Beauty','17:00:00','17:15:00');

INSERT INTO room_maintenance (room_id,begin_time,end_time) VALUES ( select id from meeting_room where name='Inspire','9:00:00','9:15:00');
INSERT INTO room_maintenance (room_id,begin_time,end_time) VALUES ( select id from meeting_room where name='Inspire','13:00:00','13:15:00');
INSERT INTO room_maintenance (room_id,begin_time,end_time) VALUES ( select id from meeting_room where name='Inspire','17:00:00','17:15:00');

INSERT INTO room_maintenance (room_id,begin_time,end_time) VALUES ( select id from meeting_room where name='Strive','9:00:00','9:15:00');
INSERT INTO room_maintenance (room_id,begin_time,end_time) VALUES ( select id from meeting_room where name='Strive','13:00:00','13:15:00');
INSERT INTO room_maintenance (room_id,begin_time,end_time) VALUES ( select id from meeting_room where name='Strive','17:00:00','17:15:00');


INSERT INTO room_reservations (room_id,purpose,booking_date,begin_time,end_time) VALUES ( select id from meeting_room where name='Strive','meeting','2024-01-17','08:00:00','9:00:00');

