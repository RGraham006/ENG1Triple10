package com.mygdx.game.Core;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BlackTexture extends Renderable {

  public Texture texture;
  public TextureRegion textureRegion;
  public int ImageWidth, ImageHeight;
  public int width, height;

  public BlackTexture(String tex) {

    changeTextureFromPath(tex);
  }

  public void changeTexture(Texture tex){
    texture = tex;
    textureRegion.setTexture(texture);
  }


  public void changeTextureFromPath(String path){
    texture = new Texture(path);


    ImageWidth = texture.getWidth();
    ImageHeight = texture.getHeight();



    width = ImageWidth;
    height = ImageHeight;


    if(textureRegion == null)
      textureRegion = new TextureRegion(texture,width,height);

  }


  public void setImageSize(int _w,int  _h) {
    ImageWidth = _w;
    ImageHeight = _h;
  }

  public void setSize(int  _w, int  _h) {
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

  public int GetWidth()
  {
    return width;
  }
  public int GetHeight(){
    return  height;
  }



}
