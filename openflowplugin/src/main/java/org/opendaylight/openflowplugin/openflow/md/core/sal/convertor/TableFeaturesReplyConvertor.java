/**
 * Copyright (c) 2013 Ericsson. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.openflowplugin.openflow.md.core.sal.convertor;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.opendaylight.openflowplugin.api.OFConstants;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.CopyTtlInCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.CopyTtlOutCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.DecMplsTtlCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.DecNwTtlCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.GroupActionCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.OutputActionCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.PopMplsActionCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.PopPbbActionCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.PopVlanActionCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.PushMplsActionCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.PushPbbActionCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.PushVlanActionCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.SetFieldCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.SetMplsTtlActionCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.SetNwDstActionCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.SetNwSrcActionCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.SetNwTtlActionCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.SetQueueActionCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.ApplyActionsCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.ClearActionsCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.GoToTableCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.MeterCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.WriteActionsCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.WriteMetadataCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.Instruction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.augments.rev150225.ActionRelatedTableFeatureProperty;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.augments.rev150225.InstructionRelatedTableFeatureProperty;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.augments.rev150225.NextTableRelatedTableFeatureProperty;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.augments.rev150225.OxmRelatedTableFeatureProperty;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.augments.rev150225.instruction.container.instruction.choice.ExperimenterIdCase;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.augments.rev150225.table.features.properties.container.table.feature.properties.NextTableIds;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.action.rev150203.action.grouping.ActionChoice;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.action.rev150203.action.grouping.action.choice.CopyTtlInCase;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.action.rev150203.action.grouping.action.choice.CopyTtlOutCase;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.action.rev150203.action.grouping.action.choice.DecMplsTtlCase;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.action.rev150203.action.grouping.action.choice.DecNwTtlCase;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.action.rev150203.action.grouping.action.choice.GroupCase;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.action.rev150203.action.grouping.action.choice.OutputActionCase;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.action.rev150203.action.grouping.action.choice.PopMplsCase;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.action.rev150203.action.grouping.action.choice.PopPbbCase;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.action.rev150203.action.grouping.action.choice.PopVlanCase;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.action.rev150203.action.grouping.action.choice.PushMplsCase;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.action.rev150203.action.grouping.action.choice.PushPbbCase;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.action.rev150203.action.grouping.action.choice.PushVlanCase;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.action.rev150203.action.grouping.action.choice.SetFieldCase;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.action.rev150203.action.grouping.action.choice.SetMplsTtlCase;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.action.rev150203.action.grouping.action.choice.SetNwDstCase;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.action.rev150203.action.grouping.action.choice.SetNwSrcCase;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.action.rev150203.action.grouping.action.choice.SetNwTtlCase;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.action.rev150203.action.grouping.action.choice.SetQueueCase;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.action.rev150203.actions.grouping.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.instruction.rev130731.instruction.grouping.InstructionChoice;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.instruction.rev130731.instruction.grouping.instruction.choice.ApplyActionsCase;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.instruction.rev130731.instruction.grouping.instruction.choice.ClearActionsCase;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.instruction.rev130731.instruction.grouping.instruction.choice.GotoTableCase;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.instruction.rev130731.instruction.grouping.instruction.choice.MeterCase;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.instruction.rev130731.instruction.grouping.instruction.choice.WriteActionsCase;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.instruction.rev130731.instruction.grouping.instruction.choice.WriteMetadataCase;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.types.rev130731.TableFeaturesPropType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.ArpOp;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.ArpSha;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.ArpSpa;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.ArpTha;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.ArpTpa;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.EthDst;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.EthSrc;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.EthType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.Icmpv4Code;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.Icmpv4Type;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.Icmpv6Code;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.Icmpv6Type;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.InPhyPort;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.InPort;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.IpDscp;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.IpEcn;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.IpProto;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.Ipv4Dst;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.Ipv4Src;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.Ipv6Dst;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.Ipv6Exthdr;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.Ipv6Flabel;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.Ipv6NdSll;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.Ipv6NdTarget;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.Ipv6NdTll;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.Ipv6Src;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.MatchField;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.Metadata;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.MplsBos;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.MplsLabel;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.MplsTc;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.PbbIsid;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.SctpDst;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.SctpSrc;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.TcpDst;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.TcpSrc;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.TunnelId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.UdpDst;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.UdpSrc;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.VlanPcp;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.VlanVid;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.match.entries.grouping.MatchEntry;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.protocol.rev130731.multipart.reply.multipart.reply.body.multipart.reply.table.features._case.MultipartReplyTableFeatures;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.protocol.rev130731.table.features.properties.grouping.TableFeatureProperties;
import org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.TableConfig;
import org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.set.field.match.SetFieldMatch;
import org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.set.field.match.SetFieldMatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.feature.prop.type.table.feature.prop.type.MatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.feature.prop.type.table.feature.prop.type.WildcardsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.feature.prop.type.table.feature.prop.type.apply.setfield.ApplySetfieldBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.feature.prop.type.table.feature.prop.type.apply.setfield.miss.ApplySetfieldMissBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.feature.prop.type.table.feature.prop.type.match.MatchSetfieldBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.feature.prop.type.table.feature.prop.type.next.table.miss.TablesMissBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.feature.prop.type.table.feature.prop.type.wildcards.WildcardSetfieldBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.feature.prop.type.table.feature.prop.type.write.setfield.WriteSetfieldBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.feature.prop.type.table.feature.prop.type.write.setfield.miss.WriteSetfieldMissBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.features.TableFeatures;
import org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.features.TableFeaturesBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.features.table.features.TableProperties;
import org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.features.table.features.TablePropertiesBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.features.table.features.table.properties.TableFeaturePropertiesBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for converting a OF library table features to MD-SAL table
 * features.
 */
public class TableFeaturesReplyConvertor {
    private static final Logger LOG = LoggerFactory.getLogger(TableFeaturesReplyConvertor.class);

    private TableFeaturesReplyConvertor() {
        //hiding implicit constructor
    }

    private interface ActionExecutor {
        public void execute(final TableFeatureProperties property, final TableFeaturePropertiesBuilder propBuilder);
    }

    public static List<TableFeatures> toTableFeaturesReply(
            final MultipartReplyTableFeatures ofTableFeaturesList) {
        if (ofTableFeaturesList == null || ofTableFeaturesList.getTableFeatures() == null) {
            return Collections.<TableFeatures>emptyList();
        }
        List<TableFeatures> salTableFeaturesList = new ArrayList<>();
        TableFeaturesBuilder salTableFeatures = new TableFeaturesBuilder();
        for (org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.protocol.rev130731.multipart.reply.multipart.reply.body.
                multipart.reply.table.features._case.multipart.reply.table.features.TableFeatures ofTableFeatures : ofTableFeaturesList
                .getTableFeatures()) {
            salTableFeatures.setTableId(ofTableFeatures.getTableId());
            salTableFeatures.setName(ofTableFeatures.getName());
            if (ofTableFeatures.getMetadataMatch() != null) {
                salTableFeatures.setMetadataMatch(new BigInteger(OFConstants.SIGNUM_UNSIGNED, ofTableFeatures.getMetadataMatch()));
            }
            if (ofTableFeatures.getMetadataWrite() != null) {
                salTableFeatures.setMetadataWrite(new BigInteger(OFConstants.SIGNUM_UNSIGNED, ofTableFeatures.getMetadataWrite()));
            }
            if (ofTableFeatures.getConfig() != null) {
                salTableFeatures.setConfig(new TableConfig(ofTableFeatures.getConfig().isOFPTCDEPRECATEDMASK()));
            }
            salTableFeatures.setMaxEntries(ofTableFeatures.getMaxEntries());
            salTableFeatures.setTableProperties(toTableProperties(ofTableFeatures.getTableFeatureProperties()));
            salTableFeaturesList.add(salTableFeatures.build());
        }
        return salTableFeaturesList;
    }

    private static final Map<TableFeaturesPropType, ActionExecutor> TABLE_FEATURE_PROPERTY_TYPE_TO_ACTION;

    static {
        final Builder<TableFeaturesPropType, ActionExecutor> builder = ImmutableMap.builder();

        builder.put(TableFeaturesPropType.OFPTFPTINSTRUCTIONS, new ActionExecutor() {

            @Override
            public void execute(final TableFeatureProperties property, final TableFeaturePropertiesBuilder propBuilder) {
                org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.feature.prop.type.table.feature.prop.type.InstructionsBuilder instructionBuilder = new org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.feature.prop.type.table.feature.prop.type.InstructionsBuilder();
                instructionBuilder
                        .setInstructions(new org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.feature.prop.type.table.feature.prop.type.instructions.InstructionsBuilder()
                                .setInstruction(setInstructionTableFeatureProperty(property)).build());
                propBuilder.setTableFeaturePropType(instructionBuilder.build());
            }
        });
        builder.put(TableFeaturesPropType.OFPTFPTINSTRUCTIONSMISS, new ActionExecutor() {

            @Override
            public void execute(final TableFeatureProperties property, final TableFeaturePropertiesBuilder propBuilder) {
                org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.feature.prop.type.table.feature.prop.type.InstructionsMissBuilder instructionMissBuilder = new org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.feature.prop.type.table.feature.prop.type.InstructionsMissBuilder();
                instructionMissBuilder
                        .setInstructionsMiss(new org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.feature.prop.type.table.feature.prop.type.instructions.miss.InstructionsMissBuilder()
                                .setInstruction(setInstructionTableFeatureProperty(property)).build());
                propBuilder.setTableFeaturePropType(instructionMissBuilder.build());
            }
        });
        builder.put(TableFeaturesPropType.OFPTFPTNEXTTABLES, new ActionExecutor() {

            @Override
            public void execute(final TableFeatureProperties property, final TableFeaturePropertiesBuilder propBuilder) {
                org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.feature.prop.type.table.feature.prop.type.NextTableBuilder nextTableBuilder = new org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.feature.prop.type.table.feature.prop.type.NextTableBuilder();
                nextTableBuilder
                        .setTables(new org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.feature.prop.type.table.feature.prop.type.next.table.TablesBuilder()
                                .setTableIds(setNextTableFeatureProperty(property)).build());
                propBuilder.setTableFeaturePropType(nextTableBuilder.build());
            }
        });
        builder.put(TableFeaturesPropType.OFPTFPTNEXTTABLESMISS, new ActionExecutor() {

            @Override
            public void execute(final TableFeatureProperties property, final TableFeaturePropertiesBuilder propBuilder) {
                org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.feature.prop.type.table.feature.prop.type.NextTableMissBuilder nextTableMissBuilder = new org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.feature.prop.type.table.feature.prop.type.NextTableMissBuilder();
                nextTableMissBuilder
                        .setTablesMiss(new TablesMissBuilder()
                                .setTableIds(setNextTableFeatureProperty(property)).build());
                propBuilder.setTableFeaturePropType(nextTableMissBuilder.build());
            }
        });
        builder.put(TableFeaturesPropType.OFPTFPTWRITEACTIONS, new ActionExecutor() {

            @Override
            public void execute(final TableFeatureProperties property, final TableFeaturePropertiesBuilder propBuilder) {
                org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.feature.prop.type.table.feature.prop.type.WriteActionsBuilder writeActionsBuilder = new org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.feature.prop.type.table.feature.prop.type.WriteActionsBuilder();
                writeActionsBuilder
                        .setWriteActions(new org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.feature.prop.type.table.feature.prop.type.write.actions.WriteActionsBuilder()
                                .setAction(setActionTableFeatureProperty(property)).build());
                propBuilder.setTableFeaturePropType(writeActionsBuilder.build());
            }
        });
        builder.put(TableFeaturesPropType.OFPTFPTWRITEACTIONSMISS, new ActionExecutor() {

            @Override
            public void execute(final TableFeatureProperties property, final TableFeaturePropertiesBuilder propBuilder) {
                org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.feature.prop.type.table.feature.prop.type.WriteActionsMissBuilder writeActionsMissBuilder = new org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.feature.prop.type.table.feature.prop.type.WriteActionsMissBuilder();
                writeActionsMissBuilder
                        .setWriteActionsMiss(new org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.feature.prop.type.table.feature.prop.type.write.actions.miss.WriteActionsMissBuilder()
                                .setAction(setActionTableFeatureProperty(property)).build());
                propBuilder.setTableFeaturePropType(writeActionsMissBuilder.build());
            }
        });
        builder.put(TableFeaturesPropType.OFPTFPTAPPLYACTIONS, new ActionExecutor() {

            @Override
            public void execute(final TableFeatureProperties property, final TableFeaturePropertiesBuilder propBuilder) {
                org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.feature.prop.type.table.feature.prop.type.ApplyActionsBuilder applyActionsBuilder = new org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.feature.prop.type.table.feature.prop.type.ApplyActionsBuilder();
                applyActionsBuilder
                        .setApplyActions(new org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.feature.prop.type.table.feature.prop.type.apply.actions.ApplyActionsBuilder()
                                .setAction(setActionTableFeatureProperty(property)).build());
                propBuilder.setTableFeaturePropType(applyActionsBuilder.build());
            }
        });
        builder.put(TableFeaturesPropType.OFPTFPTAPPLYACTIONSMISS, new ActionExecutor() {

            @Override
            public void execute(final TableFeatureProperties property, final TableFeaturePropertiesBuilder propBuilder) {
                org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.feature.prop.type.table.feature.prop.type.ApplyActionsMissBuilder applyActionsMissBuilder = new org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.feature.prop.type.table.feature.prop.type.ApplyActionsMissBuilder();
                applyActionsMissBuilder
                        .setApplyActionsMiss(new org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.feature.prop.type.table.feature.prop.type.apply.actions.miss.ApplyActionsMissBuilder()
                                .setAction(setActionTableFeatureProperty(property)).build());
                propBuilder.setTableFeaturePropType(applyActionsMissBuilder.build());
            }
        });
        builder.put(TableFeaturesPropType.OFPTFPTMATCH, new ActionExecutor() {

            @Override
            public void execute(final TableFeatureProperties property, final TableFeaturePropertiesBuilder propBuilder) {
                MatchSetfieldBuilder matchBuilder = new MatchSetfieldBuilder();

                matchBuilder.setSetFieldMatch(setSetFieldTableFeatureProperty(property, true));
                propBuilder.setTableFeaturePropType(new MatchBuilder().setMatchSetfield(matchBuilder.build()).build());
            }
        });
        builder.put(TableFeaturesPropType.OFPTFPTWILDCARDS, new ActionExecutor() {

            @Override
            public void execute(final TableFeatureProperties property, final TableFeaturePropertiesBuilder propBuilder) {
                WildcardSetfieldBuilder wildcardsBuilder = new WildcardSetfieldBuilder();
                wildcardsBuilder.setSetFieldMatch(setSetFieldTableFeatureProperty(property, false));
                propBuilder.setTableFeaturePropType(new WildcardsBuilder().setWildcardSetfield(wildcardsBuilder.build()).build());
            }
        });
        builder.put(TableFeaturesPropType.OFPTFPTWRITESETFIELD, new ActionExecutor() {

            @Override
            public void execute(final TableFeatureProperties property, final TableFeaturePropertiesBuilder propBuilder) {
                WriteSetfieldBuilder writeSetfieldBuilder = new WriteSetfieldBuilder();
                writeSetfieldBuilder.setSetFieldMatch(setSetFieldTableFeatureProperty(property, false));
                propBuilder.setTableFeaturePropType(new
                        org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.feature.prop.type.table.feature.prop.type.WriteSetfieldBuilder().setWriteSetfield(writeSetfieldBuilder.build()).build());
            }
        });
        builder.put(TableFeaturesPropType.OFPTFPTWRITESETFIELDMISS, new ActionExecutor() {

            @Override
            public void execute(final TableFeatureProperties property, final TableFeaturePropertiesBuilder propBuilder) {
                WriteSetfieldMissBuilder writeSetfieldMissBuilder = new WriteSetfieldMissBuilder();
                writeSetfieldMissBuilder.setSetFieldMatch(setSetFieldTableFeatureProperty(property, false));
                propBuilder.setTableFeaturePropType(new
                        org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.feature.prop.type.table.feature.prop.type.WriteSetfieldMissBuilder().setWriteSetfieldMiss(writeSetfieldMissBuilder.build()).build());
            }
        });
        builder.put(TableFeaturesPropType.OFPTFPTAPPLYSETFIELD, new ActionExecutor() {

            @Override
            public void execute(final TableFeatureProperties property, final TableFeaturePropertiesBuilder propBuilder) {
                ApplySetfieldBuilder applySetfieldBuilder = new ApplySetfieldBuilder();
                applySetfieldBuilder.setSetFieldMatch(setSetFieldTableFeatureProperty(property, false));
                propBuilder.setTableFeaturePropType(new
                        org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.feature.prop.type.table.feature.prop.type.ApplySetfieldBuilder().setApplySetfield(applySetfieldBuilder.build()).build());
            }
        });
        builder.put(TableFeaturesPropType.OFPTFPTAPPLYSETFIELDMISS, new ActionExecutor() {

            @Override
            public void execute(final TableFeatureProperties property, final TableFeaturePropertiesBuilder propBuilder) {
                ApplySetfieldMissBuilder applySetfieldMissBuilder = new ApplySetfieldMissBuilder();
                applySetfieldMissBuilder.setSetFieldMatch(setSetFieldTableFeatureProperty(property, false));
                propBuilder.setTableFeaturePropType(new
                        org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.feature.prop.type.table.feature.prop.type.ApplySetfieldMissBuilder().setApplySetfieldMiss(applySetfieldMissBuilder.build()).build());
            }
        });

        builder.put(TableFeaturesPropType.OFPTFPTEXPERIMENTER, new ActionExecutor() {

            @Override
            public void execute(final TableFeatureProperties property, final TableFeaturePropertiesBuilder propBuilder) {
                LOG.debug("Experimenter Table features is unhandled");
            }
        });

        builder.put(TableFeaturesPropType.OFPTFPTEXPERIMENTERMISS, new ActionExecutor() {

            @Override
            public void execute(final TableFeatureProperties property, final TableFeaturePropertiesBuilder propBuilder) {
                LOG.debug("Experimenter miss Table features is unhandled");
            }
        });

        TABLE_FEATURE_PROPERTY_TYPE_TO_ACTION = builder.build();

    }

    private static TableProperties toTableProperties(final List<TableFeatureProperties> ofTablePropertiesList) {
        if (ofTablePropertiesList == null) {
            return new TablePropertiesBuilder()
                    .setTableFeatureProperties(
                            Collections
                                    .<org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.features.table.features.table.properties.TableFeatureProperties>emptyList())
                    .build();
        }
        List<org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.features.table.features.table.properties.TableFeatureProperties> salTablePropertiesList = new ArrayList<>();
        TableFeaturePropertiesBuilder propBuilder = new TableFeaturePropertiesBuilder();
        int index = 0;
        for (TableFeatureProperties property : ofTablePropertiesList) {
            TableFeaturesPropType propType = property.getType();

            ActionExecutor actionExecutor = TABLE_FEATURE_PROPERTY_TYPE_TO_ACTION.get(propType);
            if (actionExecutor != null) {
                actionExecutor.execute(property, propBuilder);
            } else {
                LOG.error("Unsupported table feature property : " + propType);
            }
            propBuilder.setOrder(index);

            salTablePropertiesList.add(propBuilder.build());
            index += 1;
        }

        return new TablePropertiesBuilder().setTableFeatureProperties(salTablePropertiesList).build();
    }

    private static List<Instruction> setInstructionTableFeatureProperty(final TableFeatureProperties properties) {
        List<Instruction> instructionList = new ArrayList<>();
        org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.InstructionBuilder builder = new org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.InstructionBuilder();

        int index = 0;
        for (org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.instruction.rev130731
                .instructions.grouping.Instruction currInstruction : properties
                .getAugmentation(InstructionRelatedTableFeatureProperty.class).getInstruction()) {
            InstructionChoice currInstructionType = currInstruction.getInstructionChoice();

            if (currInstructionType instanceof GotoTableCase) {
                builder.setInstruction((new GoToTableCaseBuilder()
                        .build()));
            } else if (currInstructionType instanceof WriteMetadataCase) {
                builder.setInstruction((new WriteMetadataCaseBuilder()
                        .build()));
            } else if (currInstructionType instanceof WriteActionsCase) {
                builder.setInstruction((new WriteActionsCaseBuilder()
                        .build()));
            } else if (currInstructionType instanceof ApplyActionsCase) {
                builder.setInstruction((new ApplyActionsCaseBuilder()
                        .build()));
            } else if (currInstructionType instanceof ClearActionsCase) {
                builder.setInstruction((new ClearActionsCaseBuilder()
                        .build()));
            } else if (currInstructionType instanceof MeterCase) {
                builder.setInstruction((new MeterCaseBuilder()
                        .build()));
            } else if (currInstructionType instanceof ExperimenterIdCase) {
                // TODO: Experimenter instructions are unhandled
            }

            builder.setOrder(index);
            index += 1;

            instructionList.add(builder.build());
        }
        return instructionList;
    }

    private static List<Short> setNextTableFeatureProperty(final TableFeatureProperties properties) {
        List<Short> nextTableIdsList = new ArrayList<>();
        for (NextTableIds tableId : properties.getAugmentation(NextTableRelatedTableFeatureProperty.class)
                .getNextTableIds()) {
            nextTableIdsList.add(tableId.getTableId());
        }
        return nextTableIdsList;
    }

    private static final Map<Class<?>, org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.Action> OF_TO_SAL_ACTION;

    static {
        Builder<Class<?>, org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.Action> builder = ImmutableMap.builder();

        builder.put(OutputActionCase.class, new OutputActionCaseBuilder().build());
        builder.put(GroupCase.class, new GroupActionCaseBuilder().build());
        builder.put(CopyTtlOutCase.class, new CopyTtlOutCaseBuilder().build());
        builder.put(CopyTtlInCase.class, new CopyTtlInCaseBuilder().build());
        builder.put(SetMplsTtlCase.class, new SetMplsTtlActionCaseBuilder().build());
        builder.put(DecMplsTtlCase.class, new DecMplsTtlCaseBuilder().build());
        builder.put(PushVlanCase.class, new PushVlanActionCaseBuilder().build());
        builder.put(PopVlanCase.class, new PopVlanActionCaseBuilder().build());
        builder.put(PushMplsCase.class, new PushMplsActionCaseBuilder().build());
        builder.put(PopMplsCase.class, new PopMplsActionCaseBuilder().build());
        builder.put(SetQueueCase.class, new SetQueueActionCaseBuilder().build());
        builder.put(SetNwTtlCase.class, new SetNwTtlActionCaseBuilder().build());
        builder.put(DecNwTtlCase.class, new DecNwTtlCaseBuilder().build());
        builder.put(SetFieldCase.class, new SetFieldCaseBuilder().build());
        builder.put(PushPbbCase.class, new PushPbbActionCaseBuilder().build());
        builder.put(PopPbbCase.class, new PopPbbActionCaseBuilder().build());
        builder.put(SetNwSrcCase.class, new SetNwSrcActionCaseBuilder().build());
        builder.put(SetNwDstCase.class, new SetNwDstActionCaseBuilder().build());

        OF_TO_SAL_ACTION = builder.build();
    }

    private static List<org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.Action> setActionTableFeatureProperty(
            final TableFeatureProperties properties) {
        List<org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.Action> actionList = new ArrayList<>();
        int order = 0;
        for (Action action : properties
                .getAugmentation(ActionRelatedTableFeatureProperty.class).getAction()) {
            if (action != null && null != action.getActionChoice()) {
                org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.ActionBuilder actionBuilder = new org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.ActionBuilder();

                actionBuilder.setOrder(order++);
                ActionChoice actionType = action.getActionChoice();
                org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.Action salAction = OF_TO_SAL_ACTION.get(actionType.getImplementedInterface());

                actionBuilder.setAction(salAction);
                actionList.add(actionBuilder.build());
            }
        }
        return actionList;
    }

    private static final Map<Class<? extends MatchField>, Class<? extends org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.MatchField>> OF_TO_SAL_TABLE_FEATURE_PROPERTIES;

    static {
        final Builder<Class<? extends MatchField>, Class<? extends org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.MatchField>> builder = ImmutableMap.builder();

        builder.put(ArpOp.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.ArpOp.class);
        builder.put(ArpSha.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.ArpSha.class);
        builder.put(ArpSpa.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.ArpSpa.class);
        builder.put(ArpTha.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.ArpTha.class);
        builder.put(ArpTpa.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.ArpTpa.class);
        builder.put(EthDst.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.EthDst.class);
        builder.put(EthSrc.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.EthSrc.class);
        builder.put(EthType.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.EthType.class);
        builder.put(Icmpv4Code.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.Icmpv4Code.class);
        builder.put(Icmpv4Type.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.Icmpv4Type.class);
        builder.put(Icmpv6Code.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.Icmpv6Code.class);
        builder.put(Icmpv6Type.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.Icmpv6Type.class);
        builder.put(InPhyPort.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.InPhyPort.class);
        builder.put(InPort.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.InPort.class);
        builder.put(IpDscp.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.IpDscp.class);
        builder.put(IpEcn.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.IpEcn.class);
        builder.put(IpProto.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.IpProto.class);
        builder.put(Ipv4Dst.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.Ipv4Dst.class);
        builder.put(Ipv4Src.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.Ipv4Src.class);
        builder.put(Ipv6Dst.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.Ipv6Dst.class);
        builder.put(Ipv6Exthdr.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.Ipv6Exthdr.class);
        builder.put(Ipv6Flabel.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.Ipv6Flabel.class);
        builder.put(Ipv6NdSll.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.Ipv6NdSll.class);
        builder.put(Ipv6NdTarget.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.Ipv6NdTarget.class);
        builder.put(Ipv6NdTll.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.Ipv6NdTll.class);
        builder.put(Ipv6Src.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.Ipv6Src.class);
        builder.put(Metadata.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.Metadata.class);
        builder.put(MplsBos.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.MplsBos.class);
        builder.put(MplsLabel.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.MplsLabel.class);
        builder.put(MplsTc.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.MplsTc.class);
        builder.put(PbbIsid.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.PbbIsid.class);
        builder.put(SctpDst.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.SctpDst.class);
        builder.put(SctpSrc.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.SctpSrc.class);
        builder.put(TcpDst.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.TcpDst.class);
        builder.put(TcpSrc.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.TcpSrc.class);
        builder.put(TunnelId.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.TunnelId.class);
        builder.put(UdpDst.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.UdpDst.class);
        builder.put(UdpSrc.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.UdpSrc.class);
        builder.put(VlanPcp.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.VlanPcp.class);
        builder.put(VlanVid.class, org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.VlanVid.class);

        OF_TO_SAL_TABLE_FEATURE_PROPERTIES = builder.build();
    }

    private static List<SetFieldMatch> setSetFieldTableFeatureProperty(final TableFeatureProperties properties,
                                                                       final boolean setHasMask) {
        List<SetFieldMatch> setFieldMatchList = new ArrayList<>();
        SetFieldMatchBuilder setFieldMatchBuilder = new SetFieldMatchBuilder();

        Class<? extends MatchField> ofMatchField = null;

        // This handles only OpenflowBasicClass oxm class.
        for (MatchEntry currMatch : properties.getAugmentation(OxmRelatedTableFeatureProperty.class)
                .getMatchEntry()) {
            ofMatchField = currMatch.getOxmMatchField();
            Class<? extends org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.MatchField> salMatchField = null;
            setFieldMatchBuilder.setMatchType(salMatchField);
            if (setHasMask) {
                setFieldMatchBuilder.setHasMask(currMatch.isHasMask());
            }
            setFieldMatchBuilder.setMatchType(OF_TO_SAL_TABLE_FEATURE_PROPERTIES.get(ofMatchField));
            setFieldMatchList.add(setFieldMatchBuilder.build());
        }
        return setFieldMatchList;
    }

}
