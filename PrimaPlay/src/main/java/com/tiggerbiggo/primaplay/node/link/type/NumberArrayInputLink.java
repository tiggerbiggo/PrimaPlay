package com.tiggerbiggo.primaplay.node.link.type;

import com.tiggerbiggo.primaplay.node.link.InputLink;
import com.tiggerbiggo.primaplay.node.link.Link;
import com.tiggerbiggo.primaplay.node.link.OutputLink;

public class NumberArrayInputLink extends InputLink<Double[]> {
  @Override
  public boolean link(OutputLink toLink) {
    if (canLink(toLink)) {
      this.currentLink = (NumberArrayOutputLink) toLink;
      return true;
    }
    return false;
  }

  @Override
  public boolean canLink(Link other) {
    if(other == null) return false;
    return other instanceof NumberArrayOutputLink;
  }
}
