import http from "k6/http";
import { check } from "k6";

export const options = {
  scenarios: {
    contacts: {
      executor: "per-vu-iterations",
      vus: 10, // change this prop to increase/decrease the number of parallel users
      iterations: 1,
    },
  },
};

export default function() {
    const url = "http://localhost:8080/transactions/authorize";
    const payload = JSON.stringify({
        accountId: "ee73630f-31f9-4105-b433-537fd3d79e41",
        merchantName: "Supermercados BH",
        mcc: "5412",                                        // Food MCC
        amountCents: 1000                                   // R$ 10,00
    });
    const params = {
        headers: {
            "Content-Type": "application/json",
        }
    };
    const res = http.post(url, payload, params);
    const code = res.json("code");

    check(res, {
        "approved transactions": (r) => r.status === 200 && code === "00"
    });

    check(res, {
        "insufficient balance transactions": (r) => r.status === 200 && code === "51"
    });

    check(res, {
        "server error request": (r) => r.status === 200 && code === "07"
    });
}