package org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.config.openflow.plugin.impl.rev150327;

import org.opendaylight.openflowplugin.api.openflow.OpenFlowPluginProvider;
import org.opendaylight.openflowplugin.impl.OpenFlowPluginProviderImpl;
import org.opendaylight.openflowplugin.openflow.md.util.OpenflowPortsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenFlowProviderModule extends org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.config.openflow.plugin.impl.rev150327.AbstractOpenFlowProviderModule {

    private static final Logger LOG = LoggerFactory.getLogger(OpenFlowProviderModule.class);

    public OpenFlowProviderModule(final org.opendaylight.controller.config.api.ModuleIdentifier identifier, final org.opendaylight.controller.config.api.DependencyResolver dependencyResolver) {
        super(identifier, dependencyResolver);
    }

    public OpenFlowProviderModule(final org.opendaylight.controller.config.api.ModuleIdentifier identifier, final org.opendaylight.controller.config.api.DependencyResolver dependencyResolver, final org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.config.openflow.plugin.impl.rev150327.OpenFlowProviderModule oldModule, final java.lang.AutoCloseable oldInstance) {
        super(identifier, dependencyResolver, oldModule, oldInstance);
    }

    @Override
    public void customValidation() {
        // add custom validation form module attributes here.
    }

    @Override
    public java.lang.AutoCloseable createInstance() {
        LOG.info("Initializing new OFP southbound.");
        OpenflowPortsUtil.init();
        final OpenFlowPluginProvider openflowPluginProvider = new OpenFlowPluginProviderImpl(getRpcRequestsQuota(), getGlobalNotificationQuota());

        openflowPluginProvider.setSwitchConnectionProviders(getOpenflowSwitchConnectionProviderDependency());
        openflowPluginProvider.setDataBroker(getDataBrokerDependency());
        openflowPluginProvider.setRpcProviderRegistry(getRpcRegistryDependency());
        openflowPluginProvider.setNotificationProviderService(getNotificationAdapterDependency());
        openflowPluginProvider.setNotificationPublishService(getNotificationPublishAdapterDependency());
        openflowPluginProvider.setSwitchFeaturesMandatory(getSwitchFeaturesMandatory());
        openflowPluginProvider.setIsStatisticsPollingOff(getIsStatisticsPollingOff());
        openflowPluginProvider.setEntityOwnershipService(getEntityOwnershipServiceDependency());
        openflowPluginProvider.setIsStatisticsRpcEnabled(getIsStatisticsRpcEnabled());
        openflowPluginProvider.setBarrierCountLimit(getBarrierCountLimit().getValue());
        openflowPluginProvider.setBarrierInterval(getBarrierIntervalTimeoutLimit().getValue());
        openflowPluginProvider.setEchoReplyTimeout(getEchoReplyTimeout().getValue());

        openflowPluginProvider.initialize();

        LOG.info("Configured values, StatisticsPollingOff:{}, SwitchFeaturesMandatory:{}, BarrierCountLimit:{}, BarrierTimeoutLimit:{}, EchoReplyTimeout:{}",
                getIsStatisticsPollingOff(), getSwitchFeaturesMandatory(), getBarrierCountLimit().getValue(),
                getBarrierIntervalTimeoutLimit().getValue(), getEchoReplyTimeout().getValue());


        return openflowPluginProvider;
    }

}
