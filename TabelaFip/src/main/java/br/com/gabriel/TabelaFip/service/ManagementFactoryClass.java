package br.com.gabriel.TabelaFip.service;

import java.lang.management.ManagementFactory;

public class ManagementFactoryClass {
    public static void main(String[] args) {
        com.sun.management.OperatingSystemMXBean mxbean =
                (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        System.out.println("Memoria Total : " + mxbean.getTotalPhysicalMemorySize() + " Bytes ");

        System.out.println("Memoria disponivel : " + mxbean.getFreePhysicalMemorySize() +  " Bytes");

    }
}
