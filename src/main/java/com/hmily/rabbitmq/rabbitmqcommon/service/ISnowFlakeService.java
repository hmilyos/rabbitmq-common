package com.hmily.rabbitmq.rabbitmqcommon.service;

public interface ISnowFlakeService {

	long getSnowFlakeID();

    long[] getSnowFlakeIDs(int size);
}
