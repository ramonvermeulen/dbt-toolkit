import type { Node, NodeTypes } from "reactflow";
import { PositionLoggerNode } from "./PositionLoggerNode";

export type LineageInfo = {
  parents: string[];
  children: string[];
  node: string;
}

export const initialNodes = [
  { id: "a", position: { x: 0, y: 0 }, data: { label: "Please open a dbt model..." } },
] satisfies Node[];

export const nodeTypes = {
  "position-logger": PositionLoggerNode,
  // Add any of your custom nodes here!
} satisfies NodeTypes;
