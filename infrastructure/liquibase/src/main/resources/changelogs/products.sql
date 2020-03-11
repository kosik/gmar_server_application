INSERT IGNORE INTO `product` (`id`, `article_number`, `title`, `description`, `unit_price`, `currency_code`, `vat_percent`, `setup_fee`, `subscription_interval`, `subscription_number_events`, `subscription_trial`, `subscription_duration`, `subscription_cancellation`, `active`)
            VALUES
            (1, '1', '1 Monat', '', 12.60, 'EUR', 19, 0.00, '1 month', 0, '7 day', '0', '0', 00000001),
            (2, '2', '6 Monate', '', 65.50, 'EUR', 19, 0.00, '6 month', 0, '7 day', '0', '0', 00000001),
            (3, '3', '12 Monate', '', 100.74, 'EUR', 19, 0.00, '12 month', 0, '7 day', '0', '0', 00000001),
            (4, 'legacy-3M', '3 Monate', '', 11.90, 'EUR', 19, 0.00, '1 month', 0, '0', '3 month', '0', 00000001),
            (5, 'legacy-6M', '6 Monate', '', 9.90, 'EUR', 19, 0.00, '1 month', 0, '0', '6 month', '0', 00000001),
            (6, 'legacy-free', 'Free Produkt', '', 0.00, 'EUR', 19, 0.00, '1 month', 0, '0', '0', '0', 00000001);