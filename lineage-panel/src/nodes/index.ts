export type DNode = {
  id: string;
  tests: string[];
  isSelected: boolean;
  relativePath?: string;
}

export type DEdge = {
  parent: string;
  child: string;
}

export type LineageInfo = {
  nodes: DNode[];
  edges: DEdge[];
}
