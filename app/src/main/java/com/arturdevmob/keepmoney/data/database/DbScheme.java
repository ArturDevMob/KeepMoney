package com.arturdevmob.keepmoney.data.database;

public class DbScheme {
    public static final String DB_NAME = "keepmoney_database";
    public static final int DB_VERSION = 1;

    public class Account {
        public static final String TABLE_NAME = "account";

        public class Cool {
            public static final String ID = "id";
            public static final String OPENING_BALANCE = "opening_balance";
            public static final String CREATE_DATE = "create_date";
            public static final String CURRENCY_TYPE = "currency_type";
        }
    }

    public class Transaction {
        public static final String TABLE_NAME = "account_transaction";

        public class Cool {
            public static final String ID = "id";
            public static final String ACCOUNT_ID = "account_id";
            public static final String CATEGORY_ID = "category_id";
            public static final String TRANSACTION_TYPE = "transaction_type";
            public static final String CREATE_DATE = "create_date";
        }
    }

    public class Category {
        public static final String TABLE_NAME = "transaction_category";

        public class Cool {
            public static final String ID = "id";
            public static final String PARENT_ID = "parent_id";
        }
    }

    public class CurrencyPairRate {
        public static final String TABLE_NAME = "rate_currency_pair";

        public class Cool {
            public static final String PAIR_CURRENCY = "pair_currency";
            public static final String CREATE_DATE = "create_date";
        }
    }
}
