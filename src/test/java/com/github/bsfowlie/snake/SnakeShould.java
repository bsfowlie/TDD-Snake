package com.github.bsfowlie.snake;

import java.util.stream.IntStream;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SnakeShould implements WithAssertions {

    public static final int START_X = 5;

    public static final int START_Y = 5;

    private Snake snake;

    @BeforeEach void setUp() {

        snake = new Snake(new Point2D(START_X, START_Y));

    }

    @Test void move_right() {

        // given
        snake.move(Direction.RIGHT);

        // when
        snake.update();

        // then
        assertThat(snake.position()).isEqualTo(new Point2D(START_X + 1, START_Y));

    }

    @Test void move_down() {

        // given
        snake.move(Direction.DOWN);

        // when
        snake.update();

        // then
        assertThat(snake.position()).isEqualTo(new Point2D(START_X, START_Y + 1));

    }

    @Test void move_left() {

        // given
        snake.move(Direction.LEFT);

        // when
        snake.update();

        // then
        assertThat(snake.position()).isEqualTo(new Point2D(START_X - 1, START_Y));

    }

    @Test void move_up() {

        // given
        snake.move(Direction.UP);

        // when
        snake.update();

        // then
        assertThat(snake.position()).isEqualTo(new Point2D(START_X, START_Y - 1));

    }

    @Test void collide_with_food() {

        // given
        final Food food = new Food(new Point2D(START_X, START_Y));

        // when

        // then
        assertThat(snake.collidesWith(food)).isTrue();

    }

    @Test void have_initial_length_of_1() {

        // given

        // when

        // then
        assertThat(snake.body()).hasSize(1);

    }

    @Test void always_contain_head_in_body() {

        // given
        snake.move(Direction.UP);

        // when
        snake.update();

        // then
        assertThat(snake.body()).contains(snake.position());

    }

    @Test void be_able_to_grow() {

        // given
        snake.move(Direction.RIGHT);
        snake.update();

        // when
        snake.grow();

        // then
        assertThat(snake.body()).hasSize(2).contains(snake.position(), new Point2D(START_X, START_Y));

    }

    @Test void always_contain_head_at_front_of_body() {

        // given
        snake.move(Direction.DOWN);
        snake.update();
        snake.grow();

        // when
        snake.update();

        // then
        assertThat(snake.body().get(0)).isEqualTo(snake.position());

    }

    @Test void not_be_dead() {

        // given

        // when

        // then
        assertThat(snake.isDead()).isFalse();
    }

    @Test void die_if_runs_into_self() {

        // given
        snake.move(Direction.LEFT);
        IntStream.range(0, 5).forEach(i -> {
            snake.update();
            snake.grow();
        });

        // when
        snake.move(Direction.UP);
        snake.update();
        snake.move(Direction.RIGHT);
        snake.update();
        snake.move(Direction.DOWN);
        snake.update();

        // then
        assertThat(snake.isDead()).isTrue();
    }

    @Test void know_if_its_in_bounds() {

        // given
        final Rectangle boundary = new Rectangle(START_X, START_Y, 1, 1);

        // when

        // then
        assertThat(snake.inBounds(boundary)).isTrue();

    }

    @Test void know_if_its_out_of_bounds_right() {

        // given
        final Rectangle boundary = new Rectangle(START_X, START_Y, 1, 1);

        // when
        snake.move(Direction.RIGHT);
        snake.update();

        // then
        assertThat(snake.inBounds(boundary)).isFalse();

    }

    @Test void know_if_its_out_of_bounds_down() {

        // given
        final Rectangle boundary = new Rectangle(START_X, START_Y, 1, 1);

        // when
        snake.move(Direction.DOWN);
        snake.update();

        // then
        assertThat(snake.inBounds(boundary)).isFalse();

    }

    @Test void know_if_its_out_of_bounds_left() {

        // given
        final Rectangle boundary = new Rectangle(START_X, START_Y, 1, 1);

        // when
        snake.move(Direction.LEFT);
        snake.update();

        // then
        assertThat(snake.inBounds(boundary)).isFalse();

    }

    @Test void know_if_its_out_of_bounds_up() {

        // given
        final Rectangle boundary = new Rectangle(START_X, START_Y, 1, 1);

        // when
        snake.move(Direction.UP);
        snake.update();

        // then
        assertThat(snake.inBounds(boundary)).isFalse();

    }

}