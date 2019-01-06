package model.parser;

import model.Channel;
import model.Program;

import java.util.List;

/**
 * Author: Sebastian Arledal c17sal
 * 2019-01-06
 *
 * Interface Parser
 */
public interface Parser {

    List<Channel> parseChannels(String url);

    List<Program> parsePrograms(String url);
}
