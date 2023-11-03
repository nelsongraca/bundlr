package com.flowkode.bundlr.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import jakarta.enterprise.inject.spi.InjectionPoint
import jakarta.inject.Singleton
import jakarta.ws.rs.Produces


class YamlMapperProducer {

    @Produces
    @Singleton
    @YamlMapper
    fun getYamlMapper(): ObjectMapper {
        return ObjectMapper(YAMLFactory()).findAndRegisterModules().registerKotlinModule()
    }
}