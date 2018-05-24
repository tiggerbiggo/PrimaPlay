package com.tiggerbiggo.primaplay.node.implemented;

import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.core.RenderParams;
import com.tiggerbiggo.primaplay.node.core.NodeHasInput;
import com.tiggerbiggo.primaplay.node.core.NodeHasOutput;
import com.tiggerbiggo.primaplay.node.link.InputLink;
import com.tiggerbiggo.primaplay.node.link.OutputLink;
import com.tiggerbiggo.primaplay.node.link.type.ColorArrayInputLink;
import com.tiggerbiggo.primaplay.node.link.type.ColorArrayOutputLink;
import com.tiggerbiggo.primaplay.node.link.type.VectorInputLink;
import com.tiggerbiggo.primaplay.node.link.type.VectorOutputLink;

import java.awt.*;

public class SuperSampleNode implements NodeHasInput, NodeHasOutput {
    ColorArrayInputLink input;
    ColorArrayOutputLink output;
    private int factor;

    public SuperSampleNode(int factor) {
        this.factor = factor;

        input = new ColorArrayInputLink();
        output = new ColorArrayOutputLink() {
            @Override
            public Color[] get(RenderParams p) {
                RenderParams p2 = new RenderParams(p.width() * factor, p.height() * factor, 0, 0, p.n());

                double[] r, g, b;
                r = new double[p.n()];
                g = new double[p.n()];
                b = new double[p.n()];
                for (int i = 0; i < factor; i++) {
                    for (int j = 0; j < factor; j++) {
                        p2.setX((p.x() * factor) + i);
                        p2.setY((p.y() * factor) + j);

                        //System.out.println("x, y: " + p2.x() + ", "+ p2.y());

                        Color[] cA = input.get(p2);
                        for(int k=0; k<cA.length; k++){
                            r[k] += cA[k].getRed();

                            g[k] += cA[k].getGreen();
                            b[k] += cA[k].getBlue();
                        }
                        //System.out.println("i, j: " + i + ", " + j + ", r: " + r[0] + ", cA[0]: " + cA[0].getRed());
                    }
                    //System.out.println();
                }
                //System.out.println("_______________");

                int fac2 = factor*factor;

                Color[] toReturn = new Color[p.n()];
                for(int i=0; i<toReturn.length; i++){
                    toReturn[i] = new Color(
                            (int)(r[i]/fac2),
                            (int)(g[i]/fac2),
                            (int)(b[i]/fac2)
                    );
                }

                return toReturn;
            }
        };
    }

    public SuperSampleNode() {
        this(2);
    }

    @Override
    public InputLink<?>[] getInputs() {
        return new InputLink[]{input};
    }

    @Override
    public InputLink<?> getInput(int n) {
        return input;
    }

    @Override
    public OutputLink<?>[] getOutputs() {
        return new OutputLink[]{output};
    }

    @Override
    public OutputLink<?> getOutput(int n) {
        return output;
    }
}