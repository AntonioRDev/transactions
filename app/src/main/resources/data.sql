INSERT INTO account (id, personName) VALUES (uuid('ee73630f-31f9-4105-b433-537fd3d79e41'), 'Alice');
INSERT INTO balanceaccount (id, accountId, category, balanceCents) VALUES (uuid('dcd8728f-4279-4c76-9211-4880078b8321'), uuid('ee73630f-31f9-4105-b433-537fd3d79e41'), 'CASH', 10_00);
INSERT INTO balanceaccount (id, accountId, category, balanceCents) VALUES (uuid('086676ac-56d5-4678-a335-ef698dd03a22'), uuid('ee73630f-31f9-4105-b433-537fd3d79e41'), 'FOOD', 1_000_00);
INSERT INTO merchant (id, name, mcc) VALUES (uuid('f1b3b3b4-1b3b-4b3b-9b3b-3b3b3b3b3b3b'), 'Supermercados BH', '5411');