package com.jetbrains.fintechpayment.bdd

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/commonTest/resources/features"],
    glue = ["com.jetbrains.fintechpayment.bdd"],
    plugin = ["pretty"]
)
class CucumberTestRunner
