package com.tiggerbiggo.primaplay.node.implemented.io.iterative;

import ch.rs.reflectorgrid.TransferGrid;
import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.core.RenderParams;
import com.tiggerbiggo.primaplay.node.core.INodeHasInput;
import com.tiggerbiggo.primaplay.node.core.INodeHasOutput;
import com.tiggerbiggo.primaplay.node.link.type.VectorInputLink;
import com.tiggerbiggo.primaplay.node.link.type.VectorOutputLink;

public abstract class IterativeNode implements INodeHasInput, INodeHasOutput {

  VectorInputLink in;
  VectorOutputLink out;

  @TransferGrid
  private int iter;

  IterativeNode(int _iter) {
    this.iter = _iter;

    in = new VectorInputLink();
    addInput(in);

    out = new VectorOutputLink() {
      @Override
      public Vector2 get(RenderParams p) {
        Vector2 z = initZ(p);
        Vector2 c = initC(p);
        for (int i = 0; i < iter; i++) {
          z = transform(z, c, i);
          if (escapeCheck(z, i)) {
            return onEscape(z, i);
          }
        }
        return onBound(z, iter);
      }
    };
    addOutput(out);
  }

  public abstract Vector2 initZ(RenderParams p);

  public abstract Vector2 initC(RenderParams p);

  public abstract Vector2 transform(Vector2 z, Vector2 c, int currentIteration);

  public abstract Vector2 onEscape(Vector2 in, int currentIteration);

  public abstract Vector2 onBound(Vector2 in, int currentIteration);

  public abstract boolean escapeCheck(Vector2 in, int currentIteration);
}
