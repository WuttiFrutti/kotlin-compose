package entities.objects;

import entities.identifiers.NodeId;

import java.util.List;

public class SupplyChainNode {
    public NodeId id;
    public List<SupplyChainNode> children;
}
