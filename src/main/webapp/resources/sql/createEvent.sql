CREATE EVENT IF NOT EXISTS autoDeleteUser
ON SCHEDULE every 30 minute 
STARTS '2024-06-11 14:20:00' 
ON COMPLETION PRESERVE 
  COMMENT 'match auto delete User' 
DO 
  UPDATE DB¿Ã∏ß.Posts 
  SET isMatched=2 
  WHERE isMatched=0 AND reserved_at<=NOW();