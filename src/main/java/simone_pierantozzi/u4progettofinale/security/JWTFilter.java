package simone_pierantozzi.u4progettofinale.security;

import tools.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import simone_pierantozzi.u4progettofinale.entities.User;
import simone_pierantozzi.u4progettofinale.exceptions.UnauthorizedException;
import simone_pierantozzi.u4progettofinale.payloads.ErrorsDTO;
import simone_pierantozzi.u4progettofinale.services.UserService;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class JWTFilter extends OncePerRequestFilter {

	private final JWTTools jwtTools;
	private final UserService userService;
	private final ObjectMapper objectMapper;

	public JWTFilter(JWTTools jwtTools, UserService userService, ObjectMapper objectMapper) {
		this.jwtTools = jwtTools;
		this.userService = userService;
		this.objectMapper = objectMapper;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			String header = request.getHeader("Authorization");
			if (header == null || !header.startsWith("Bearer "))
				throw new UnauthorizedException("Inserire il token nell'header Authorization");

			String accessToken = header.replace("Bearer ", "");
			jwtTools.verifyToken(accessToken);

			Long currentUserId = jwtTools.extractIdFromToken(accessToken);
			User currentUser = userService.findById(currentUserId);

			Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser, null, currentUser.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);

			filterChain.doFilter(request, response);
		} catch (UnauthorizedException ex) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			ErrorsDTO error = new ErrorsDTO(ex.getMessage(), LocalDateTime.now());
			response.getWriter().write(objectMapper.writeValueAsString(error));
		}
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return new AntPathMatcher().match("/api/auth/**", request.getServletPath());
	}
}
