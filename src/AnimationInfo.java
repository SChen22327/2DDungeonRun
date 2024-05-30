public class AnimationInfo {
    private String name;
    private Animation animation;
    public AnimationInfo(String n, Animation a) {
        name = n;
        animation = a;
    }
    public String getName() {
        return name;
    }

    public Animation getAnimation() {
        return animation;
    }
}
