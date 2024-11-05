-- Insert test users

-- Insert test projects
INSERT INTO project (id, name, description, created_date) VALUES
                                                             (1, 'Project Alpha', 'Description for Project Alpha', '2023-01-01'),
                                                             (2, 'Project Beta', 'Description for Project Beta', '2023-02-01'),
                                                             (3, 'Project Gamma', 'Description for Project Gamma', '2023-03-01');

-- Insert test user-project relationships
INSERT INTO users_project (projectid, userid) VALUES
                                                                   (1, 1),
                                                                   (2, 2),
                                                                   (3, 3),
                                                                   (1, 2),
                                                                   (2, 3);
