package com.bluewhale.medlog.common.application.usecase;

public interface UseCase<I, O> {

    O execute(I input);
}
