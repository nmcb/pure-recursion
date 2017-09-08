ack     ::  Int -> Int -> Int
ack m n
  | m == 0           =  n + 1
  | n == 0 && m > 0  =  ack (m - 1) 1
  | m > 0  && n > 0  =  ack (m - 1) (ack m (n - 1))
  
