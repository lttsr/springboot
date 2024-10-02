truncate company_user cascade;
insert into company_user (user_id, group_id, name, name_kana, mail_address, profile, description, authority_type, status_type) values('jiro_yamada', 'management_group', '山田 次郎', 'ヤマダ ジロウ', 'jirou_yamada@example.com', 'シンカーミクセル所属(管理者)', 'シンカーミクセルからの応援', 0, 1);
