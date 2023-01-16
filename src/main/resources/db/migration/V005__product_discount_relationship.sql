UPDATE products SET discount_id = (SELECT discount_id FROM discounts WHERE discount_type = 'FIXED_PRICE' and amount < 2)
WHERE code = '100';

UPDATE products SET discount_id = (SELECT discount_id FROM discounts WHERE discount_type = 'PERCENT' and after_piece = 2)
WHERE code = '101';

UPDATE products SET discount_id = (SELECT discount_id FROM discounts WHERE discount_type = 'FIXED_PRICE' and amount = 10)
WHERE code = '200';

UPDATE products SET discount_id = (SELECT discount_id FROM discounts WHERE discount_type = 'PERCENT' and after_piece = 1)
WHERE code = '210';

UPDATE products SET discount_id = (SELECT discount_id FROM discounts WHERE discount_type = 'FIXED_PRICE' and amount = 10)
WHERE code = '220';
