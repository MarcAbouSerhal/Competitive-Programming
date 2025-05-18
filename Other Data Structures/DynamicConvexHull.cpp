using ll = long long;
struct Line {
	mutable ll m, b, p;
	bool operator<(const Line& o) const { return m < o.m; }
	bool operator<(ll x) const { return p < x; }
};
struct DynamicConvexHull : multiset<Line, less<>> {
	static const double inf = 1/.0;
	bool isect(iterator x, iterator y) {
		if (y == end()) return x->p = inf, 0;
		if (x->m == y->m) x->p = x->b > y->b ? inf : -inf;
		else x->p = (y->b - x->b) / (x->m - y->m);
		return x->p >= y->p;
	}
	void add(ll m, ll b) {
		auto z = insert({m, b, 0}), y = z++, x = y;
		while (isect(y, z)) z = erase(z);
		if (x != begin() && isect(--x, y)) isect(x, y = erase(y));
		while ((y = x) != begin() && (--x)->p >= y->p) isect(x, erase(y));
	}
	Line query(ll x) {
		return *lower_bound(x);
	}
};
