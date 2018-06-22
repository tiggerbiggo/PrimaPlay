package com.tiggerbiggo.primaplay.node.implemented.out;

import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.core.RenderParams;
import com.tiggerbiggo.primaplay.node.core.NodeHasOutput;
import com.tiggerbiggo.primaplay.node.link.type.VectorOutputLink;

public class PixelMapNode extends NodeHasOutput {

  VectorOutputLink out;
  PixelMap map;

  public PixelMapNode(PixelMap map) {
    this.map = map;
    out = new VectorOutputLink() {
      @Override
      public Vector2 get(RenderParams p) {
        return map.get(p.x(), p.y());
      }
    };
    addOutput(out);
  }

  public static PixelMapNode square(int x, int y) {
    PixelMap map = new PixelMap(x, y);
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < y; j++) {
        double b = Math.max(i, j);
        map.set(i, j, new Vector2(b, b));
      }
    }
    return new PixelMapNode(map);
  }

  @Override
  public String getName() {
    return "Pixel Map Node";
  }

  @Override
  public String getDescription() {
    return "Uses a Pixel Map to sample from, effectively a pre-set image.";
  }
}