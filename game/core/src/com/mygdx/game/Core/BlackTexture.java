package com.mygdx.game.Core;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BlackTexture extends Renderable {

  public Texture texture;
  public TextureRegion textureRegion;
  public float ImageWidth, ImageHeight;
  public float width, height;

  public BlackTexture(String tex) {

    texture = new Texture(tex);

    ImageWidth = texture.getWidth();
    ImageHeight = texture.getHeight();

    width = ImageWidth;
    height = ImageHeight;

  }


  public void setImageSize(float _w, float _h) {
    ImageWidth = _w;
    ImageHeight = _h;
  }

  public void setSize(float _w, float _h) {
    width = _w;
    height = _h;

  }

  /**
   * Sets wraps
   *
   * @param U
   * @param V
   */
  public void setWrap(Texture.TextureWrap U, Texture.TextureWrap V) {

    texture.setWrap(U, V);
  }

  public void setWrap(Texture.TextureWrap UV) {
    setWrap(UV, UV);
  }

  @Override
  public void Render(SpriteBatch batch, float x, float y) {
    batch.draw(textureRegion, x, y, width, height);
  }


}
