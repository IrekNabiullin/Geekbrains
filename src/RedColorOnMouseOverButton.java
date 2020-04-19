public static class RedColorOnMouseOverButton extends TextButton {

      private boolean hover;

      public MyButton(String text, Skin skin) {
         super(text, skin);
         addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
               hover = true;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
               hover = false;
            }
         });
      }

      @Override
      public Color getColor() {
         if (!hover)
            return super.getColor();
         else
            return Color.RED;
      }
   }