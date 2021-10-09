-- Insert user info
INSERT into public.tb_user (username, email, passwd)
    VALUES ('example', 'example@example.com', MD5('example'));