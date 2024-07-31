CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS Transaction;
DROP TABLE IF EXISTS BalanceAccount;
DROP TABLE IF EXISTS Account;
DROP TABLE IF EXISTS Merchant;

CREATE TABLE IF NOT EXISTS Account (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY NOT NULL,
    personName TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS Transaction (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY NOT NULL,
    accountId UUID NOT NULL,
    mcc TEXT NOT NULL,
    merchantName TEXT NOT NULL,
    amountCents INT NOT NULL,
    createdAt DATE NOT NULL,
    FOREIGN KEY (accountId) REFERENCES Account(id)
);

CREATE TABLE IF NOT EXISTS Merchant (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY NOT NULL,
    name TEXT NOT NULL,
    mcc TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS BalanceAccount (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY NOT NULL,
    accountId UUID NOT NULL,
    category TEXT NOT NULL,
    balanceCents INT NOT NULL,
    FOREIGN KEY (accountId) REFERENCES Account(id)
);