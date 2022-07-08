package net.omkov.alum.callback;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import java.util.Optional;
import net.minecraft.client.option.SimpleOption.SliderCallbacks;

public enum GammaSliderCallbacks implements SliderCallbacks<Double> {
	INSTANCE;

	@Override
	public Optional<Double> validate(Double d) {
		return d >= 0.0 && d <= 16.0 ? Optional.of(d) : Optional.empty();
	}

	@Override
	public Codec<Double> codec() {
		return Codec.either(Codec.doubleRange(0.0, 16.0), Codec.BOOL).xmap(either -> either.map(value -> value, value -> value != false ? 1.0 : 0.0), Either::left);
	}

	@Override
	public double toSliderProgress(Double d) {
		return d;
	}

	@Override
	public Double toValue(double d) {
		return d;
	}
}
