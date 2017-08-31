-- Set up only known period - 2012-2013
INSERT INTO INCOME_RATE_PERIOD (ID, START_DATE, END_DATE) VALUES (1, '2012-07-01', '2013-06-30')

-- Set up income period tax brackets
INSERT INTO INCOME_RATE_TAX_BRACKET (ID, INCOME_RATE_PERIOD_ID, BRACKET_START_VALUE, TAX_RATE_PERCENTAGE) VALUES (1, 1, 0, 0) 

INSERT INTO INCOME_RATE_TAX_BRACKET (ID, INCOME_RATE_PERIOD_ID, BRACKET_START_VALUE, TAX_RATE_PERCENTAGE) VALUES (2, 1, 18200, 0.19)

INSERT INTO INCOME_RATE_TAX_BRACKET (ID, INCOME_RATE_PERIOD_ID, BRACKET_START_VALUE, TAX_RATE_PERCENTAGE) VALUES (3, 1, 37000, 0.325)

INSERT INTO INCOME_RATE_TAX_BRACKET (ID, INCOME_RATE_PERIOD_ID, BRACKET_START_VALUE, TAX_RATE_PERCENTAGE) VALUES (4, 1, 80000, 0.37)

INSERT INTO INCOME_RATE_TAX_BRACKET (ID, INCOME_RATE_PERIOD_ID, BRACKET_START_VALUE, TAX_RATE_PERCENTAGE) VALUES (5, 1, 180000, 0.45)