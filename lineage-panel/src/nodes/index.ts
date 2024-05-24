export type DNode = {
  id: string;
  tests: string[];
  isSelected: boolean;
}

export type DEdge = {
  parent: DNode;
  child: DNode;
}

export type LineageInfo = {
  nodes: DNode[];
  edges: DEdge[];
}
